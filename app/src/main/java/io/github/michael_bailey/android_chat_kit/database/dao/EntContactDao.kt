package io.github.michael_bailey.android_chat_kit.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.michael_bailey.android_chat_kit.database.entity.EntUser
import kotlinx.coroutines.flow.Flow

@Dao
interface EntContactDao {
	@Query(
		"""
			SELECT * FROM contact
		"""
	)
	fun genAllContacts(): Flow<List<EntUser>>
	
	@Insert
	fun insertContact(contact: EntUser)
	
	
	
	
}