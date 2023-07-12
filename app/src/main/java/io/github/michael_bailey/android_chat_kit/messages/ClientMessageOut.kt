package io.github.michael_bailey.android_chat_kit.messages

import io.github.michael_bailey.android_chat_kit.utils.serialisation.UUIDSerialiser
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class ClientMessageOut: Message() {
	@Serializable
	public class Info()
	
	@Serializable
	public class Connect (
		@Serializable(with=UUIDSerialiser::class)
		val uuid: UUID,
		val username: String,
		val address: String,
	)
}