package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import java.security.PublicKey
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * # ServerRepository
 *
 * Repository for interacting with saved server data
 *
 * > Don't use this or fetching server data, only the local server data store.
 * > @see [ServerInfoRepository]
 */
@Singleton
class MutableServerRepository @Inject constructor(
	private val serverDao: EntServerDao,
): ServerRepository(
	serverDao = serverDao
) {
	
	suspend fun addServer(
		hostname: String,
		port: Int = 5600,
		@Suppress
		publicKey: PublicKey? = null
	): EntServer {
		
		// Not this repositories job to fetch information
		val server = EntServer(
			host = hostname,
			port = port,
		)
		
		serverDao.insert(server)
		
		return server
	}
	
	suspend fun updateServer(server: EntServer) {
		serverDao.updateServer(server = server)
	}
	
	suspend fun removeServer(uuid: UUID) {
		serverDao.deleteServer(uuid)
	}
}
