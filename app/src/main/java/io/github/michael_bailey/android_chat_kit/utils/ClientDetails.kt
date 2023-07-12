package io.github.michael_bailey.android_chat_kit.utils

import io.github.michael_bailey.android_chat_kit.utils.serialisation.UUIDSerialiser
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ClientDetails(
	@Serializable(with=UUIDSerialiser::class)
	val uuid: UUID,
	val username: String,
	val address: String,
)
