package io.github.michael_bailey.android_chat_kit.utils.serialisation

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

object UUIDSerialiser: KSerializer<UUID> {
	override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
	
	override fun deserialize(decoder: Decoder): UUID {
		return UUID.fromString(decoder.decodeString())
	}
	
	override fun serialize(encoder: Encoder, value: UUID) {
		encoder.encodeString(value.toString())
	}
}