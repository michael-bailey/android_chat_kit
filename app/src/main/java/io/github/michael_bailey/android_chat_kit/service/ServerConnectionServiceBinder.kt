package io.github.michael_bailey.android_chat_kit.service

import android.os.Binder
import io.github.michael_bailey.android_chat_kit.extension.any.log
import java.util.UUID

class ServerConnectionServiceBinder(
	private val service: ServerConnectionService
): Binder() {
	
	fun connect(serverId: UUID) {
		log("Connecting to server")
		service.connect(serverId)
	}
	
	fun disconnect() {
		service.disconnect()
	}
	
	fun isConnected(): Boolean {
		TODO("Not yet implemented")
	}
	
	fun isConnectedTo(serverId: UUID?): Boolean {
		TODO("Not yet implemented")
	}
}