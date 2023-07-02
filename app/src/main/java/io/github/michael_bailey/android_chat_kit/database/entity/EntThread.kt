package io.github.michael_bailey.android_chat_kit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(
	tableName = "thread",
	indices = []
)
data class EntThread(
	@PrimaryKey var uuid: UUID = UUID.randomUUID(),
	var createdTime: LocalDateTime = LocalDateTime.now(),
	var owner: UUID,
) {
}
