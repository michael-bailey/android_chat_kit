package io.github.michael_bailey.android_chat_kit.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(
	tableName = "contact",
	indices = []
)
@Parcelize
data class EntContact(
	@PrimaryKey var uuid: UUID,
	var username: String,
	var nickname: String = "",
): Parcelable
