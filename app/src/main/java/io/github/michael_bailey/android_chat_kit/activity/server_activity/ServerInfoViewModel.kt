package io.github.michael_bailey.android_chat_kit.activity.server_activity

import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ViewModelScoped
class ServerInfoViewModel @Inject constructor(
	private val serverInfoRepository: ServerInfoRepository,
) {
	private val _serverHostname = MutableStateFlow("<NONE>")
	private val _serverName = MutableStateFlow("<NONE>")
	private val _serverOwner = MutableStateFlow("<NONE>")
	
	val hostname: Flow<String> = _serverHostname
	val name: Flow<String> = _serverName
	val owner: Flow<String> = _serverOwner
	
	suspend fun fetchInfo(hostname: String, port: Int) {
		val infoData = serverInfoRepository.fetchInfo(hostname, port)
		
		infoData?.also {
			_serverHostname.emit(it.hostname)
			_serverName.emit(it.name)
			_serverOwner.emit(it.owner)
		}
	}
}