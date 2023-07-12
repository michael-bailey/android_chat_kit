package io.github.michael_bailey.android_chat_kit.utils.network

import io.github.michael_bailey.android_chat_kit.messages.ClientMessageIn
import io.github.michael_bailey.android_chat_kit.messages.ClientMessageOut
import io.github.michael_bailey.android_chat_kit.utils.ClientDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONException
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Closeable
import java.net.Socket
import java.util.UUID

class ServerConnectionSession(
	address: String,
	port: Int = 5600,
	private val coroutineScope: CoroutineScope
): Closeable {
	
	private val details: ClientDetails = ClientDetails(
		username = "test",
		uuid = UUID.randomUUID(),
		address = "127.0.0.1"
	)
	private var isClosed = false
	
	private var socket: Socket
	private var inputStream: BufferedReader
	private var outputStream: BufferedWriter
	
	init {
		socket = Socket(address, port)
		inputStream = socket.getInputStream().bufferedReader()
		outputStream = socket.getOutputStream().bufferedWriter()
		
		val msgString = inputStream.readLine()
		
		val message = Json.decodeFromString(ClientMessageIn.serializer(), msgString)
		
		coroutineScope.launch { handleMessage(message) }
	}
	
	tailrec suspend fun readLoop() {
		
		readLoop()
	}
	
	private suspend inline fun handleMessage(message: ClientMessageIn) {
		when (message) {
			is ClientMessageIn.Request -> handleRequest()
			is ClientMessageIn.Connecting -> handleConnecting()
			else -> throw JSONException("incoming message invalid for this type")
		}
	}
	
	private suspend fun handleConnecting() = withContext(Dispatchers.IO) {
	
	}
	
	private suspend fun handleRequest() = withContext(Dispatchers.IO) {
		val obj = this@ServerConnectionSession
		val details = obj.details
		val connectMsg = ClientMessageOut.Connect(details.uuid, details.username, details.address)
		outputStream.write(
			Json.encodeToString(ClientMessageOut.Connect.serializer(), connectMsg) + '\n'
		)
	}
	
	enum class ConnectionStatus {
		Disconnected, Connecting, Connected, Disconnecting
	}
	
	override fun close() {
		TODO("Not yet implemented")
	}
}