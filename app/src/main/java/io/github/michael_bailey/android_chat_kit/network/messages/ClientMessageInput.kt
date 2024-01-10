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

/**
 * Messages sent from the client to the server
 */
@Serializable
sealed class ClientMessageInput {
	
	/**
	 * requests the current client list
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("GetClients")
	data object GetClients: ClientMessageInput()
	
	/**
	 * requests the current global message list
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("GetMessages")
	data object GetMessages: ClientMessageInput()
	
	/**
	 * Sends a message to a specific user.
	 *
	 * @property to [UUID] The id of the receiving user.
	 * @property content [String] The content of the message
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("SendMessage")
	data class SendMessage(
		@Serializable(with= UUIDSerialiser::class)
		val to: UUID,
		val content: String
	): ClientMessageInput()
	
	/**
	 * Sends a message to everyone
	 *
	 * @property content [String] The content of the message
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("SendGlobalMessage")
	data class SendGlobalMessage(
		val content: String
	): ClientMessageInput()
	
	/**
	 * Notifies the server that this client is disconnecting.
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("Disconnect")
	data object Disconnect: ClientMessageInput()

}
