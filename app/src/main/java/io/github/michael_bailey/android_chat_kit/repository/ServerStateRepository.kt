package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionState
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateConnected
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateConnecting
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateDisconnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerStateRepository @Inject constructor(
): Repository() {
	
	
	private val _connectionStatus = MutableStateFlow<ServerConnectionState>(ServerConnectionStateDisconnected)
	val connectionState: Flow<ServerConnectionState> = _connectionStatus
	
	val connectedServer: Flow<ServerData?> = _connectionStatus.map {
		when(it) {
			is ServerConnectionStateDisconnected -> null
			is ServerConnectionStateConnecting -> it.data
			is ServerConnectionStateConnected -> it.serverData
		}
	}
	
	val connectionStatusText = _connectionStatus.map {
		when(it) {
			is ServerConnectionStateDisconnected -> "Disconnected"
			is ServerConnectionStateConnecting -> "Connecting"
			is ServerConnectionStateConnected -> "Connected"
		}
	}
	
	suspend fun onStateChange(new: ServerConnectionState) {
		_connectionStatus.emit(new)
	}
}
