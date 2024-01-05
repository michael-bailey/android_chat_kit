/*
 * Copyright (c) 2023.
 * This Software is Property of British Information Technologies.
 * All software that utilises or derives from this resource must link back to our source
 */

package org.british_information_technologies.chatkit_server_kotlin.lib.messages

import io.github.michael_bailey.android_chat_kit.utils.serialisation.UUIDSerialiser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class ClientMessageInput {

	@Serializable
	@SerialName("GetClients")
	data object GetClients: ClientMessageInput()

	@Serializable
	@SerialName("GetMessages")
	data object GetMessages: ClientMessageInput()

	@Serializable
	@SerialName("SendMessage")
	data class SendMessage(
		@Serializable(with= UUIDSerialiser::class)
		val to: UUID,
		val content: String
	): ClientMessageInput()

	@Serializable
	@SerialName("SendGlobalMessage")
	data class SendGlobalMessage(
		val content: String
	): ClientMessageInput()

	@Serializable
	@SerialName("Disconnect")
	data object Disconnect: ClientMessageInput()

}
