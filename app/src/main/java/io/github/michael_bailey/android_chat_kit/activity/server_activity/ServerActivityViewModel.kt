package io.github.michael_bailey.android_chat_kit.activity.server_activity

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.data_type.UserChatMessageData
import io.github.michael_bailey.android_chat_kit.database.entity.EntGlobalChatMessage
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.extension.view_model.launch
import io.github.michael_bailey.android_chat_kit.interfaces.IEventSocketDelegate
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedServerViewModel
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedUserViewModel
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IServerChatListViewModel
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerSocketRepository
import io.github.michael_bailey.android_chat_kit.repository.UserListRepository
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionServiceBinder
import kotlinx.coroutines.Job
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput
import java.util.UUID
import javax.inject.Inject

/**
 * view state of the Server activity
 * controls the lifecycle of connections to the server.
 *
 * @property _serverSocket [EventSocket?] The socket to the currently connected server
 *
 * @property serverName [Flow<String>] The name of the server.
 * @property serverOwner [Flow<String>] The name of the server.
 * @property serverHostname [Flow<String>] The name of the server.
 *
 * @property users [Flow<String>] A list of currently connected users.
 * @property globalMessages [Flow<String>] A list of all messages on the server.
 *
 * @author michael-bailey
 * @since 1.0
 */
@HiltViewModel
class ServerActivityViewModel @Inject constructor(
	private val serverInfoViewModel: ServerInfoViewModel,
	private val serverChatViewModel: ServerChatViewModel,
	private val serverUserViewModel: ServerUserViewModel,
	private val userListRepository: UserListRepository,
	private val messageRepository: MessageRepository,
	private val serverSocketRepository: ServerSocketRepository,
): ViewModel(),
	IConnectedServerViewModel by serverInfoViewModel,
	IServerChatListViewModel by serverChatViewModel,
	IConnectedUserViewModel by serverUserViewModel,
	DefaultLifecycleObserver,
	IEventSocketDelegate,
	ServiceConnection
{
	
	lateinit var binder: ServerConnectionServiceBinder
	
	val users = userListRepository.userList.asLiveData()
	
	/**
	 * Get an updating store of messages, for a particular user
	 *
	 * @param uuid: [UUID] the id of the users message list
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	fun getUserMessageStore(uuid: UUID): LiveData<List<UserChatMessageData>> {
		return messageRepository.getUserMessageStore(uuid)
	}
	
	/**
	 * Sends a text message to the global server channel.
	 *
	 * @param msg [String] The message to be sent.
	 *
	 * @return [Job] The coroutine jon that is ran.
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	fun sendGlobalMessage(msg: String): Job = launch {
		serverChatViewModel.sendGlobalMessage(msg)
	}
	
	/**
	 * Sends a message to a specific user.
	 *
	 * @param uuid [UUID] The uuid of the user.
	 * @param message [String] The message content.
	 *
	 * @return [Job] The coroutine jon that is ran.
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	fun sendUserMessage(uuid: UUID, message: String): Job = launch {
		serverChatViewModel.sendUserMessage(uuid, message)
		messageRepository.addSentUserMessage(
			to = uuid,
			content  = message
		)
	}
	
	private fun addUserMessage(msg: ClientMessageOutput.UserMessage) = launch {
		messageRepository.addUserMessage(
			from = msg.from,
			content = msg.content
		)
	}
	
	private suspend fun addGlobalMessage(
		msg: ClientMessageOutput.GlobalMessage
	) = launch {
		messageRepository.addGlobalMessage(EntGlobalChatMessage(
			fromServer = serverId.value!!,
			from = msg.from,
			content = msg.content
		))
	}
	
	private suspend fun updateGlobalMessages(msg: ClientMessageOutput.GlobalChatMessages) {
		messageRepository.update(
			msg.messages.map {
				EntGlobalChatMessage(
					fromServer = serverId.value!!,
					from = it.from,
					content = it.content
				)
			}
		)
	}
	
	private suspend fun updateClientList(msg: ClientMessageOutput.ConnectedClients) {
		userListRepository.updateList(msg.clients)
	}
	
	// MARK: - Activity lifecycle events
	
	override fun onCreate(owner: LifecycleOwner) {
		super.onCreate(owner)
		
		log("onCreate: getting activity")
		
		owner as ServerActivity
		
		log("onCreate: getting server id")
		
		val intent = owner.intent
		val server_id = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			intent.getSerializableExtra("server_id", UUID::class.java)
		} else {
			intent.getSerializableExtra("server_id") as UUID
		}
		
		
		
		if (server_id == null) {
			log("onCreate: server id is null, finishing")
			owner.finish()
		}
		
		if (binder.isConnected() && !binder.isConnectedTo(server_id) ) {
			log("onCreate: service already connected")
			binder.disconnect()
		}
		
		
		log("onCreate: telling service to connect to server id")
		
		binder.connect(server_id!!)
	}
	
	override suspend fun onMessageReceived(msg: ClientMessageOutput) {
		when(msg) {
			is ClientMessageOutput.ConnectedClients -> { this.updateClientList(msg) }
			is ClientMessageOutput.GlobalMessage -> { this.addGlobalMessage(msg) }
			is ClientMessageOutput.GlobalChatMessages -> { this.updateGlobalMessages(msg) }
			is ClientMessageOutput.UserMessage -> {this.addUserMessage(msg)}
			else -> {log("other message received")}
		}
	}
	
	override fun onStop(owner: LifecycleOwner) {
		super.onDestroy(owner)
		
		launch {
			serverSocketRepository.disconnect()
		}
	}
	
	
	
	override fun onServiceConnected(name: ComponentName, binder: IBinder) {
		this.binder = binder as ServerConnectionServiceBinder
	}
	
	override fun onServiceDisconnected(p0: ComponentName?) {
		log("Binder disconnected?")
	}
}

