package io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerStateRepository
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionServiceBinder
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DebugActivityServersViewModel @Inject constructor(
	private val serverStateRepository: ServerStateRepository,
	private val serverInfoRepository: ServerInfoRepository,
): ViewModel(), ServiceConnection {
	
	private lateinit var binder: ServerConnectionServiceBinder

	val connectedServer = serverStateRepository.connectedServer.asLiveData()
	
	val servers = serverInfoRepository.servers.asLiveData()
	
	val connectionStatus = serverStateRepository.connectionStatusText.asLiveData()
	val connectionState = serverStateRepository.connectionState.asLiveData()
	
	fun getServerFlow(uuid: UUID): Flow<ServerData?> = serverInfoRepository.genServerInfo(uuid)
	
	fun dial(uuid: UUID) {
		binder.connect(uuid)
	}
	
	fun disconnect() {
		binder.disconnect()
	}
	
	fun reload(uuid: UUID) {
	
	}
	
	fun delete(uuid: UUID) {
	
	}
	
	// ServiceConnection methods
	
	override fun onServiceConnected(component: ComponentName?, binder: IBinder?) {
		this.binder = binder as ServerConnectionServiceBinder
	}
	
	override fun onServiceDisconnected(p0: ComponentName?) {  }
	

	
	
}