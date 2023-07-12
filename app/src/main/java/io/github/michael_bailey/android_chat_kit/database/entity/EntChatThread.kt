package io.github.michael_bailey.android_chat_kit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
	tableName = "chat_thread",
	indices = []
)
data class EntChatThread(
	@PrimaryKey var uuid: UUID,
	var name: String = "",
)
