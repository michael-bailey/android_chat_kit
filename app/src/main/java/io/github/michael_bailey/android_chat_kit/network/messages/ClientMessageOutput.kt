/*
 * Copyright (c) 2023.
 * This Software is Property of British Information Technologies.
 * All software that utilises or derives from this resource must link back to our source
 */

package org.british_information_technologies.chatkit_server_kotlin.lib.messages

import io.github.michael_bailey.android_chat_kit.utils.ClientDetails
import io.github.michael_bailey.android_chat_kit.utils.serialisation.UUIDSerialiser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class ClientMessageOutput {

	@Serializable
	@SerialName("Connected")
	data object Connected: ClientMessageOutput()

	@Serializable
	@SerialName("ConnectedClients")
	data class ConnectedClients(
		val clients: List<ClientDetails>
	): ClientMessageOutput()

	@Serializable
	@SerialName("GlobalChatMessages")
	data class GlobalChatMessages(
		val messages: List<GlobalMessage>
	): ClientMessageOutput()

	@Serializable
	@SerialName("UserMessage")
	data class UserMessage(
		@Serializable(with= UUIDSerialiser::class)
		val from: UUID,
		val content: String
	): ClientMessageOutput()

	@Serializable
	@SerialName("GlobalMessage")
	data class GlobalMessage(
		@Serializable(with= UUIDSerialiser::class)
		val from: UUID,
		@Serializable(with= UUIDSerialiser::class)
		val id: UUID? = null,
		val content: String,
		val time: String? = null,
	): ClientMessageOutput()

	@Serializable
	@SerialName("ClientConnected")
	data class ClientConnected(
		@Serializable(with= UUIDSerialiser::class)
		val id: UUID,
		val username: String
	): ClientMessageOutput()

	@Serializable
	@SerialName("ClientRemoved")
	data class ClientRemoved(
		@Serializable(with= UUIDSerialiser::class)
		val id: UUID
	): ClientMessageOutput()


	@Serializable
	@SerialName("Disconnected")
	data object Disconnected: ClientMessageOutput()
}