package io.github.michael_bailey.android_chat_kit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
	tableName = "contact",
	indices = []
)
data class EntContact(
	@PrimaryKey var uuid: UUID,
	var username: String,
	var nickname: String = "",
)
