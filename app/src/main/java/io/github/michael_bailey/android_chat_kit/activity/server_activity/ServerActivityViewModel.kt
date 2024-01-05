package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.data_type.GlobalChatMessage
import io.github.michael_bailey.android_chat_kit.database.entity.EntGlobalChatMessage
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.extension.view_model.launch
import io.github.michael_bailey.android_chat_kit.interfaces.IEventSocketDelegate
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedServerViewModel
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.UserListRepository
import io.github.michael_bailey.android_chat_kit.utils.ClientDetails
import io.github.michael_bailey.android_chat_kit.utils.EventSocket
import kotlinx.coroutines.Job
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput
import javax.inject.Inject

@HiltViewModel
class ServerActivityViewModel @Inject constructor(
	private val serverInfoViewModel: ServerInfoViewModel,
	private val loginRepository: LoginRepository,
	private val userListRepository: UserListRepository,
	private val messageRepository: MessageRepository,
): ViewModel(), DefaultLifecycleObserver, IConnectedServerViewModel, IEventSocketDelegate {
	
	/// the socket that controls communication
	// between the app and the server
	private var _serverSocket: EventSocket? = null
	
	override val serverName: LiveData<String> = serverInfoViewModel
		.name.asLiveData()
	
	override val serverOwner: LiveData<String> = serverInfoViewModel
		.owner.asLiveData()
	
	override val serverHostname: LiveData<String> = serverInfoViewModel
		.hostname.asLiveData()
	
	override val users: LiveData<List<ClientDetails>> = userListRepository.userList.asLiveData()
	override val groupedMessage: LiveData<List<List<GlobalChatMessage>>> = messageRepository.globalMessages.asLiveData()
	
	// MARK: - UI Handlers
	override fun sendGlobalMessage(msg: String): Job = launch {
		_serverSocket!!.send(ClientMessageInput.SendGlobalMessage(msg))
	}
	
	// MARK: - Message handlers
	
	override suspend fun onMessageReceived(msg: ClientMessageOutput) {
		when(msg) {
			is ClientMessageOutput.ConnectedClients -> { this.updateClientList(msg) }
			is ClientMessageOutput.GlobalMessage -> { this.addGlobalMessage(msg) }
			is ClientMessageOutput.GlobalChatMessages -> { this.updateGlobalMessages(msg) }
			else -> {log("other message received")}
		}
	}
	
	private suspend fun addGlobalMessage(
		msg: ClientMessageOutput.GlobalMessage
	) {
		messageRepository.addMessage(EntGlobalChatMessage(
			sender = msg.from,
			content = msg.content
		))
	}
	
	private suspend fun updateGlobalMessages(msg: ClientMessageOutput.GlobalChatMessages) {
		messageRepository.update(
			msg.messages.map {
				EntGlobalChatMessage(
					sender = it.from,
					content = it.content
				)
			}
		)
	}
	
	private suspend fun updateClientList(msg: ClientMessageOutput.ConnectedClients) {
		userListRepository.updateList(msg.clients)
	}
	
	// MARK: - Activity lifecycle events
	
	/// When the activity is created
	/// Create the connection
	override fun onCreate(owner: LifecycleOwner) {
		super.onCreate(owner)
		
		log("onCreate: getting activity")
		
		owner as ServerActivity
		
		val hostname = owner.intent.extras?.getString("hostname")
		val port = owner.intent.extras?.getInt("port") ?: 5600
		
		if (hostname == null) {
			owner.finish()
			log("Hostname is null")
			return
		}
		
		_serverSocket = EventSocket(this)
		
		launch {
			
			serverInfoViewModel.fetchInfo(hostname, port)
			
			_serverSocket!!.connect(
				hostname,
				port,
				loginRepository.getUUID()!!,
				loginRepository.getUsername()
			)
			
			_serverSocket!!.send(ClientMessageInput.GetClients)
			_serverSocket!!.send(ClientMessageInput.GetMessages)
		}
	}
	
	override fun onStart(owner: LifecycleOwner) {
		super.onStart(owner)
	}
	
	override fun onStop(owner: LifecycleOwner) {
		super.onStop(owner)
	}
	
	override fun onDestroy(owner: LifecycleOwner) {
		super.onDestroy(owner)
		launch {
			_serverSocket?.send(ClientMessageInput.Disconnect)
			_serverSocket = null
		}
	}
}