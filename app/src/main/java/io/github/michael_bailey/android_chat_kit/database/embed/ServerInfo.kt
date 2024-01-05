package io.github.michael_bailey.android_chat_kit.database.embed

import java.time.LocalDateTime


data class ServerInfo(
	// not used atm, this is updated by the dao
	var updatedTime: LocalDateTime = LocalDateTime.now(),
	
	var name: String,
	var owner: String,
	var alias: String,
)