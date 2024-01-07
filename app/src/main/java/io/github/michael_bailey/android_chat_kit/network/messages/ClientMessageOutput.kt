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

/**
 * All messages sent from the server, whilst connected.
 *
 * @author michael-bailey
 */
@Serializable
sealed class ClientMessageOutput {
	
	/**
	 * Tells the user that they've been successfully connected
	 *
	 * @author michael-bailey
	 */
	@Serializable
	@SerialName("Connected")
	data object Connected: ClientMessageOutput()
	
	/**
	 * A updated list of connected clients.
	 *
	 * @property clients The list of connected clients
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("ConnectedClients")
	data class ConnectedClients(
		val clients: List<ClientDetails>
	): ClientMessageOutput()
	
	/**
	 * A updated list of global chat messages
	 *
	 * @property messages The list of messages
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("GlobalChatMessages")
	data class GlobalChatMessages(
		val messages: List<GlobalMessage>
	): ClientMessageOutput()
	
	/**
	 * A message received by a user
	 *
	 * @property from The id of the user that sent the message
	 * @property content Content of the message sent
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("UserMessage")
	data class UserMessage(
		@Serializable(with= UUIDSerialiser::class)
		val from: UUID,
		val content: String
	): ClientMessageOutput()
	
	/**
	 * A message sent to the global server channel.
	 *
	 * @property id The message ID, this will be used later for reply threads.
	 * @property from The uuid of the sender of the message.
	 * @property content The message content.
	 * @property time The time the message was been sent.
	 *
	 * @author michael-bailey
	 * @since 1.0
	 */
	@Serializable
	@SerialName("GlobalMessage")
	data class GlobalMessage(
		@Serializable(with= UUIDSerialiser::class)
		val id: UUID? = null,
		@Serializable(with= UUIDSerialiser::class)
		val from: UUID,
		val content: String,
		val time: String? = null,
	): ClientMessageOutput()
	
	/**
	 * Not used for now.
	 * Represents a user that has connected.
	 *
	 * @property id The if of the user
	 * @property username the username of the user connected
	 *
	 * @author michael-bailey
	 */
	@Serializable
	@SerialName("ClientConnected")
	data class ClientConnected(
		@Serializable(with= UUIDSerialiser::class)
		val id: UUID,
		val username: String
	): ClientMessageOutput()
	
	/**
	 * Not used for now.
	 * Represents a user that has connected.
	 *
	 * @property id The if of the user
	 *
	 * @author michael-bailey
	 */
	@Serializable
	@SerialName("ClientRemoved")
	data class ClientRemoved(
		@Serializable(with= UUIDSerialiser::class)
		val id: UUID
	): ClientMessageOutput()
	
	/**
	 * Tells when the server has disconnected you
	 *
	 * @author michael-bailey
	 */
	@Serializable
	@SerialName("Disconnected")
	data object Disconnected: ClientMessageOutput()
}