package io.github.michael_bailey.android_chat_kit.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Entity(
	tableName = "message",
	indices = []
)
@Parcelize
data class EntMessage(
	@PrimaryKey var uuid: UUID = UUID.randomUUID(),
	var createdTime: LocalDateTime = LocalDateTime.now(),
	var content: String
): Parcelable
