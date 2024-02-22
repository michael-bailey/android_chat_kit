package io.github.michael_bailey.android_chat_kit.utils

import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.extension.buffered_reader.genReadClientMsg
import io.github.michael_bailey.android_chat_kit.extension.buffered_reader.genReadNetworkMsg
import io.github.michael_bailey.android_chat_kit.extension.buffered_writer.genWriteClientMsg
import io.github.michael_bailey.android_chat_kit.extension.buffered_writer.genWriteNetworkMsg
import io.github.michael_bailey.android_chat_kit.interfaces.IEventSocketDelegate
import io.github.michael_bailey.android_chat_kit.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageOutput
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException
import java.util.UUID

class EventSocket(
	private val delegate: IEventSocketDelegate
) : Repository(), IEventSocketDelegate by delegate {
	
	private var _socket: Socket? = null
	protected var _writer: BufferedWriter? = null
	private var _writerJob: Job? = null
	private var _reader: BufferedReader? = null
	private var _readerJob: Job? = null
	
	private val _messageQueue = Channel<ClientMessageInput>()
	
	private val _isConnected = MutableStateFlow(false)
	suspend fun connect(
		hostname: String,
		port: Int = 5600,
		uuid: UUID,
		username: String
	) {
		
		try {
		
		} catch (e: IOException) {
			log("")
		} catch (e: UnknownHostException) {
		
		}
		
		_socket = Socket(hostname, port)
		_reader = _socket!!.getInputStream().bufferedReader()
		_writer = _socket!!.getOutputStream().bufferedWriter()
		
		val request = _reader!!.genReadNetworkMsg()
		
		if (request !is NetworkMessageOutput.Request) {
			log("received was not request")
			return
		}
		
		_writer!!.genWriteNetworkMsg(
			NetworkMessageInput.Connect(
				uuid = uuid,
				username = username,
				address = ""
			)
		)
		
		// wait for connection to complete
		
		val conencted = _reader!!.genReadClientMsg()
		
		if (conencted !is ClientMessageOutput.Connected) {
			log("connected was not received")
			return
		}
		
		// create jobs for reading data
		
		_readerJob = scope.launch(Dispatchers.IO) { readerLoop() }
		
		_writerJob = scope.launch(Dispatchers.IO) { writerLoop() }
		
		// get connected clients
		_writer!!.genWriteClientMsg(ClientMessageInput.GetClients)
		
		// get global messages
		_writer!!.genWriteClientMsg(ClientMessageInput.GetMessages)
	}
	
	private suspend fun readerLoop() {
		while (_socket!!.isConnected) {
			try {
				val msg = _reader!!.genReadClientMsg()
				onMessageReceived(msg)
			} catch (e: Exception) {
				log("Exception: ${e.message}")
			}
		}
	}
	
	private suspend fun writerLoop() {
		while (_socket!!.isConnected) {
			if (_writer == null) {
				continue
			}
			
			val msg = _messageQueue.receive()
			_writer!!.genWriteClientMsg(msg)
		}
	}
	
	suspend fun send(msg: ClientMessageInput) {
		_messageQueue.send(msg)
	}
}