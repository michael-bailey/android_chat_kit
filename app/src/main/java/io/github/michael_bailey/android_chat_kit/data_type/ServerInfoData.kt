package io.github.michael_bailey.android_chat_kit.data_type

data class ServerInfoData(
	val hostname: String,
	val port: Int = 5600,
	val name: String,
	val owner: String,
)
