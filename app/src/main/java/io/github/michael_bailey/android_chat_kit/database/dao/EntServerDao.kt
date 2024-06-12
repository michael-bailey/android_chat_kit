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
	
	/**
	 * gets all server id's
	 * updates on addition or deletion of servers
	 */
	@Query(
		"""
			SELECT uuid FROM server
		"""
	) abstract fun genServerIds(): Flow<List<UUID>>
	
	@Query(
		"""
			SELECT uuid FROM server
		"""
	) abstract suspend fun getServerIds(): List<UUID>
	
	
	
	@Query(
		"""
			SELECT * FROM server
		"""
	)
	abstract fun genServers(): Flow<List<EntServer>>
	
	/**
	 * Gets the full details of a server.
	 *
	 * @param id: [UUID] the id of the server to flow.
	 *
	 * @return [Flow<EntServer?>] A flow that updates if the server information changes.
	 */
	@Query(
		"""
			SELECT * FROM server
			WHERE :id = server.uuid
			LIMIT 1
		"""
	) abstract fun genServer(id: UUID): Flow<EntServer?>
	
	/**
	 * get the server with teh specified ID
	 */
	@Query(
		"""
			SELECT * FROM server
			WHERE uuid = :uuid
		"""
	)
	abstract suspend fun getServer(uuid: UUID): EntServer?
	
	/**
	 * Adds a server to the database
	 */
	@Insert
	abstract suspend fun insert(server: EntServer)
	
	/**
	 * Updates an existing server with the new information
	 */
	@Update
	abstract suspend fun updateServer(server: EntServer)
	
	/**
	 * deletes the server with the specified ID
	 */
	@Query(
		"""
			DELETE FROM server
			WHERE uuid = :uuid
		"""
	)
	abstract suspend fun deleteServer(uuid: UUID)
	
	
}