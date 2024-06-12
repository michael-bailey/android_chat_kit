package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.lifecycle.asLiveData
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedServerViewModel
import io.github.michael_bailey.android_chat_kit.repository.MutableServerRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject

/**
 *
 */
@ViewModelScoped
class ServerInfoViewModel @Inject constructor(
	private val serverInfoRepository: ServerInfoRepository,
	private val savedServerRepository: MutableServerRepository
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

	}
	
	
}