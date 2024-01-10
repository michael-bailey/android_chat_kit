package io.github.michael_bailey.android_chat_kit.data_type

import java.time.LocalDateTime
import java.util.UUID

data class GlobalChatMessageData(
	val senderId: UUID,
	val sender: String,
	val isReceived: Boolean,
	val content: String,
	val received: LocalDateTime
)
