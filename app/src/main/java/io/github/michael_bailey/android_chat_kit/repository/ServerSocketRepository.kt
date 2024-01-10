package io.github.michael_bailey.android_chat_kit.repository

import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.interfaces.IEventSocketDelegate
import io.github.michael_bailey.android_chat_kit.utils.EventSocket
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageInput
import java.util.UUID
import javax.inject.Inject

@ViewModelScoped
class ServerSocketRepository @Inject constructor(
) {
	/// the socket that controls communication
	// between the app and the server
	protected var _serverSocket: EventSocket? = null
	
	suspend fun connect(
		delegate: IEventSocketDelegate,
		hostname: String,
		port: Int,
		userID: UUID,
		username: String,
		) {
		_serverSocket = EventSocket(delegate)
		_serverSocket!!.connect(
			hostname,
			port,
			userID,
			username
		)
	}
	
	// MARK: - UI Handlers
	suspend fun sendGlobalMessage(msg: String) {
		_serverSocket!!.send(ClientMessageInput.SendGlobalMessage(msg))
	}
	
	suspend fun getClients() {
		_serverSocket!!.send(ClientMessageInput.GetClients)
	}
	
	suspend fun getMessages() {
		_serverSocket!!.send(ClientMessageInput.GetMessages)
	}
	
	suspend fun sendUserMessage(uuid: UUID, message: String) {
		_serverSocket!!.send(ClientMessageInput.SendMessage(uuid, message))
	}
	
	suspend fun disconnect() {
		_serverSocket?.send(ClientMessageInput.Disconnect)
		_serverSocket = null
	}
	

	
}