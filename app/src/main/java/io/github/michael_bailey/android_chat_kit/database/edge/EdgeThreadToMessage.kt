package io.github.michael_bailey.android_chat_kit.database.edge

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
	tableName = "thread_to_contact"
)
data class EdgeThreadToMessage(
	@PrimaryKey val uuid: UUID = UUID.randomUUID(),
	val chatThread: UUID,
	val contact: UUID,
)
