package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This represents a readonly view of saved server information
 *
 * To mutate server data, @see [MutableServerRepository]
 */
@Singleton
open class ServerRepository @Inject constructor(
	private val serverDao: EntServerDao,
): Repository() {
	/** list of all server id's */
	val allServerIds = serverDao.genServerIds()
	
	/** list of all servers */
	val allServers = serverDao.genServers()
	
	/** mapping of uuid to hostname */
	val serverHostnameMap = allServers.map {
		it.associateBy({ it.uuid }, { it.host })
	}
	
	/** mapping of uuid to hostname */
	val serverNameMap = allServers.map {
		it.associateBy({ it.uuid }, { it.serverInfo?.name })
	}
	
	/** mapping of uuid to hostname */
	val serverAliasMap = allServers.map {
		it.associateBy({ it.uuid }, { it.serverInfo?.name })
	}
	
	suspend fun getAllServerIds(): List<UUID> = serverDao.getServerIds()
	
	suspend fun getServer(uuid: UUID) = serverDao.getServer(uuid)
	
	fun flowServer(serverId: UUID): Flow<EntServer?> = serverDao.genServer(serverId)
}