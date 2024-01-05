package io.github.michael_bailey.android_chat_kit.data_type

import java.time.LocalDateTime

data class GlobalChatMessage(
	val sender: String,
	val isReceived: Boolean,
	val content: String,
	val received: LocalDateTime
)
