package io.github.michael_bailey.android_chat_kit.messages

import kotlinx.serialization.Serializable

@Serializable
sealed class ClientMessageIn: Message() {
	@Serializable
	class Request: ClientMessageIn()
	
	@Serializable
	class GotInfo(
		val server_name: String,
		val server_owner: String,
	)
	
	@Serializable
	class Connecting: ClientMessageIn()
	
	@Serializable
	class Error: ClientMessageIn()
}