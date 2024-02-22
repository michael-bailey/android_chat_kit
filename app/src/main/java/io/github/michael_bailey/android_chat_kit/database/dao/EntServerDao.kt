package io.github.michael_bailey.android_chat_kit.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
abstract class EntServerDao {
	@Query(
		"""
			SELECT * FROM server
		"""
	) abstract fun genServers(): Flow<List<EntServer>>
	
	@Query(
		"""
			SELECT * FROM server
			WHERE :hostname = server.host
			LIMIT 1
		"""
	) abstract fun genServer(hostname: String): Flow<EntServer?>
	
	@Query(
		"""
			SELECT * FROM server
			WHERE :hostname == server.host
			LIMIT 1
		"""
	)
	abstract suspend fun getServer(hostname: String): EntServer?
	
	@Query(
		"""
			SELECT * FROM server
			WHERE :id = server.uuid
			LIMIT 1
		"""
	) abstract fun genServer(id: UUID): Flow<EntServer?>
	
	@Insert
	abstract suspend fun insert(server: EntServer)
	
	@Update
	abstract suspend fun updateServer(server: EntServer)
	
	@Query(
		"""
			DELETE FROM server
			WHERE uuid = :uuid
		"""
	)
	abstract suspend fun deleteServer(uuid: UUID)
}