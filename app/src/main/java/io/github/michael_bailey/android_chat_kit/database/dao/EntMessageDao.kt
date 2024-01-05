package io.github.michael_bailey.android_chat_kit.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.michael_bailey.android_chat_kit.database.entity.EntMessage
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface EntMessageDao {
	@Query(
		"""
			SELECT message.* FROM message
			JOIN chat_thread_to_message ON  chat_thread_to_message.message = message.uuid
			WHERE chat_thread_to_message.chatThread == :uuid
		"""
	)
	fun genMessageFromChatThread(uuid: UUID): Flow<List<EntMessage>>
	
	@Insert
	fun createMessage(message: EntMessage)
	
}