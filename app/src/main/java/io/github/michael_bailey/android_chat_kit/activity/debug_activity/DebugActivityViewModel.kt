package io.github.michael_bailey.android_chat_kit.activity.debug_activity

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.extension.view_model.gen
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.MutableServerRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerStateRepository
import io.github.michael_bailey.android_chat_kit.repository.UserListRepository
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionServiceBinder
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DebugActivityViewModel @Inject constructor(
	private val serverListRepository: MutableServerRepository,
	private val serverRepository: ServerInfoRepository,
	private val loginRepository: LoginRepository,
	private val userListRepository: UserListRepository,
	private val messageRepository: MessageRepository,
	private val serverStateRepository: ServerStateRepository
): ViewModel(), ServiceConnection, DefaultLifecycleObserver {
	
	private lateinit var binder: ServerConnectionServiceBinder
	
	private val _selectedServer = MutableStateFlow<UUID?>(null)
	
	val username = loginRepository.username.asLiveData()
	val uuid = loginRepository.uuid.asLiveData()
	val servers = serverRepository.servers.asLiveData()
	val selectedServer = _selectedServer.asLiveData()
	
	override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
		this.binder = binder as ServerConnectionServiceBinder
	}
	
	override fun onServiceDisconnected(name: ComponentName?) {  }
	
	fun connect(uuid: UUID) = gen {
		binder.connect(uuid)
	}
	
	fun add_server(hostname: String, port: String) = gen {
		serverListRepository.addServer(hostname, port.toInt())
	}
}