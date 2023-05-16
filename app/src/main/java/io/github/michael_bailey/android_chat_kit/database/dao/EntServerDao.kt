package io.github.michael_bailey.android_chat_kit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer

@Dao
abstract class EntServerDao {
	@Query(
		"""
			SELECT EXISTS(SELECT * FROM server)
			"""
	)
	abstract suspend fun queryServerExists(): Boolean
	
	@Query(
		"""
			SELECT EXISTS(SELECT * FROM server)
			"""
	)
	abstract fun genQueryServerExists(): LiveData<Boolean>
	
	@Query(
		"""
			SELECT * FROM server
		"""
	)
	abstract suspend fun queryServers(): List<EntServer>
	
	@Query(
		"""
			SELECT * FROM server
		"""
	)
	abstract fun genQueryServers(): LiveData<List<EntServer>>
}