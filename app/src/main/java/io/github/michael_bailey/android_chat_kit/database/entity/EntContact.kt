package io.github.michael_bailey.android_chat_kit.database.entity

import androidx.room.PrimaryKey
import java.util.UUID

data class EntContact(
	@PrimaryKey var uuid: UUID,
	var nickname: String,
)
