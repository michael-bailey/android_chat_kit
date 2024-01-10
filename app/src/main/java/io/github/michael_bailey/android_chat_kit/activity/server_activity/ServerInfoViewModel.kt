package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.lifecycle.asLiveData
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedServerViewModel
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject

/**
 *
 */
@ViewModelScoped
class ServerInfoViewModel @Inject constructor(
	private val serverInfoRepository: ServerInfoRepository,
	private val serverRepository: ServerRepository
): IConnectedServerViewModel {
	
	private val _serverId = MutableStateFlow(UUID(0,0))
	private val _serverHostname = MutableStateFlow("<NONE>")
	private val _serverName = MutableStateFlow("<NONE>")
	private val _serverOwner = MutableStateFlow("<NONE>")
	
	override val serverId = _serverId.asLiveData()
	override val serverHostname = _serverHostname.asLiveData()
	override val serverName  = _serverName.asLiveData()
	override val serverOwner = _serverOwner.asLiveData()
	
	override suspend fun fetchInfo(hostname: String, port: Int) {
		val infoData = serverInfoRepository.fetchInfo(hostname, port)
		val server = serverRepository.findServer(hostname)
		infoData?.also {
			server?.let { it1 -> _serverId.emit(it1.uuid) }
			_serverHostname.emit(it.hostname)
			_serverName.emit(it.name)
			_serverOwner.emit(it.owner)
		}
	}
	
	
}