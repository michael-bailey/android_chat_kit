package io.github.michael_bailey.android_chat_kit.utils

import android.os.Parcelable
import io.github.michael_bailey.android_chat_kit.utils.serialisation.UUIDSerialiser
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@SerialName("ClientDetails")
data class ClientDetails(
	@Serializable(with= UUIDSerialiser::class)
	val uuid: UUID,
	val username: String,
	val address: String,
	@SerialName("public_key")
	val publicKey: String?,
)
