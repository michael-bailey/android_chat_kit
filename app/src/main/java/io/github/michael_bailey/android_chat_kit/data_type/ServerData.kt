package io.github.michael_bailey.android_chat_kit.data_type

import java.util.UUID

data class ServerData(
	val uuid: UUID,
	val hostname: String,
	val port: Int = 5600,
	val name: String,
	val owner: String,
)
