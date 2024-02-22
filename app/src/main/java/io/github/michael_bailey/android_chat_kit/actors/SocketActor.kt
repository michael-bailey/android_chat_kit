package io.github.michael_bailey.android_chat_kit.actors

import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.ISocketActorDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.british_information_technologies.chatkit_server_kotlin.lib.Weak
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Closeable
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException

class SocketActor(
	initialDelegate: ISocketActorDelegate,
	private val socket: Socket,
	private val reader: BufferedReader,
	private val writer: BufferedWriter,
): Closeable {
	
	constructor(
		initialDelegate: ISocketActorDelegate,
		host: String,
		port: Int
	): this(initialDelegate, Socket(host, port))
	
	constructor(
		initialDelegate: ISocketActorDelegate,
		socket: Socket
	): this(
		initialDelegate,
		socket,
		socket.getInputStream().bufferedReader(),
		socket.getOutputStream().bufferedWriter()
	)
	
	private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
	
	private val readJob: Job
	
	private val sendActor: SendChannel<String>
	
	var delegate by Weak<ISocketActorDelegate>(initialDelegate)
	
	init {
		socket.soTimeout = 100
		readJob = scope.launch {
			do {
				try {
					val string = withContext(Dispatchers.IO) { reader.readLine() } ?: continue
					delegate?.receivedMessage(string)
				} catch (_: SocketTimeoutException) {
					delegate?.connectionDied()
					socket.close()
				} catch (_: SocketException) {
					delegate?.connectionDied()
					socket.close()
				}
			} while (!socket.isClosed)
		}
		sendActor = scope.actor {
			for (message in channel) {
				log("sending string to client")
				writer.run {
					write(message)
					newLine()
					flush()
				}
			}
			log("actor finished")
		}
	}
	
	suspend fun sendData(data: String) = sendActor.send(data)
	
	suspend fun sendAndWait(data: String) {
	
	}
	
	override fun close() {
		scope.launch {
			log("closing connection")
			socket.close()
			readJob.cancel()
			sendActor.close()
			reader.close()
			writer.close()
		}
	}
}
