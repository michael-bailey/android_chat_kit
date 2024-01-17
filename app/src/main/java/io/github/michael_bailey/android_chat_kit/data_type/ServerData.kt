package io.github.michael_bailey.android_chat_kit.data_type

import io.github.michael_bailey.android_chat_kit.data_type.sharable.SharableServerInfoData
import java.util.UUID

data class ServerData(
	val uuid: UUID? = null,
	val hostname: String,
	val port: Int = 5600,
	val name: String,
	val owner: String,
) {
	fun into(): SharableServerInfoData = SharableServerInfoData(
		hostname = hostname,
		port = port,
		name = name,
		owner = owner
	)
	
}
