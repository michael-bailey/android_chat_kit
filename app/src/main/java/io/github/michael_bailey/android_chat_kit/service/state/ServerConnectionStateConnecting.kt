package io.github.michael_bailey.android_chat_kit.service.state

import io.github.michael_bailey.android_chat_kit.data_type.ServerData

data class ServerConnectionStateConnecting(
	
	val data: ServerData,
	val notificationId: Int
): ServerConnectionState() {
}