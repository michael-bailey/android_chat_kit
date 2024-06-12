package io.github.michael_bailey.android_chat_kit.service.state

import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.extension.buffered_reader.genReadline
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.UserListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput
import java.io.BufferedWriter
import java.net.Socket

class ServerConnectionStateConnected(
	delegate: IServerConnectionStateDelegate,
	val serverData: ServerData,
	private val socket: Socket,
	private val userListRepository: UserListRepository,
	private val messageRepository: MessageRepository,
): ServerConnectionState(delegate = delegate) {
	
	private val job = Job()
	private val scope = CoroutineScope(Dispatchers.IO + job)
	
	private val readerJob: Job = scope.launch {
		val reader = socket.getInputStream().bufferedReader()
		while (this.isActive) {
			val messageString = runCatching { reader.genReadline() }
			
			if (messageString.isFailure) { this.cancel() }
			
			val message = Json.decodeFromString<ClientMessageOutput>(messageString)
			
			
		}
	}
	
	private val bufferedWriter: BufferedWriter = socket.getOutputStream().bufferedWriter()
	
	fun sendDisconnect() {
		log("Sending Disconnect message")
		val message = Json.encodeToString(
			serializer = ClientMessageInput.serializer(),
			value = ClientMessageInput.Disconnect
		)
		bufferedWriter.write(message)
		bufferedWriter.flush()
	}
}