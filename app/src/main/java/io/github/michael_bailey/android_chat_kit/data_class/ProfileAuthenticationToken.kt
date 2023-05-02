package io.github.michael_bailey.android_chat_kit.data_class

import java.io.Serializable
import java.util.UUID


data class ProfileAuthenticationToken(
	val userid: UUID,
	val signed: String,
): Serializable {
}