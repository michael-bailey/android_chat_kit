package io.github.michael_bailey.android_chat_kit.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.michael_bailey.android_chat_kit.database.entity.EntContact
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface EntContactDao {
	@Query(
		"""
			SELECT * FROM contact
		"""
	)
	fun genAllContacts(): Flow<List<EntContact>>
	
	@Query(
		"""
			SELECT contact.* FROM contact
			JOIN chat_thread_to_contact ON contact.uuid == chat_thread_to_contact.contact
			WHERE chat_thread_to_contact.chatThread == :chatThreadID
		"""
	)
	fun genContactsFromChatThread(chatThreadID: UUID): Flow<List<EntContact>>
	
	
	
	@Insert
	fun insertContact(contact: EntContact)
	
	
	
	
}