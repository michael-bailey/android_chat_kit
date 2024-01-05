package io.github.michael_bailey.android_chat_kit.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Entity(
	tableName = "thread",
	indices = []
)
@Parcelize
data class EntThread(
	@PrimaryKey var uuid: UUID = UUID.randomUUID(),
	var createdTime: LocalDateTime = LocalDateTime.now(),
	var owner: UUID,
): Parcelable
