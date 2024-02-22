package io.github.michael_bailey.android_chat_kit.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

/**
 * Data model for a message received from another user
 *
 * @property id The id of the message
 * @property createdTime The time this entity was created.
 * @property from The id of the user this message is from
 * @property threadId The id of the message thread this message is replying to.
 * @property content Th e content of the message
 *
 * @author michael-bailey
 *
 * @since
 */
@Entity(
	tableName = "global_chat_message",
	indices = []
)
@Parcelize
data class EntGlobalChatMessage(
	@PrimaryKey var uuid: UUID = UUID.randomUUID(),
	var createdTime: LocalDateTime = LocalDateTime.now(),
	
	var fromServer: UUID,
	var from: UUID,
	
	var threadId: UUID? = null,
	var sendTime: LocalDateTime? = null,
	
	var content: String
): Parcelable
