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
sealed class NetworkMessageInput {
	
	@Serializable
	@SerialName("Info")
	object Info: NetworkMessageInput()
	
	@Serializable
	@SerialName("Connect")
	data class Connect(
		@Serializable(with=UUIDSerialiser::class)
		val uuid: UUID,
		val username: String,
		val address: String,
	): NetworkMessageInput()
}