package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.data_type.ServerInfoData
import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.embed.ServerInfo
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import kotlinx.coroutines.flow.Flow
import java.security.PublicKey
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * # ServerRepository
 *
 * Repository for interacting with saved server data
 *
 * > Don't use this or fetching server data, only th elocal server data store.
 * > This is because this does not fetch anything from the internet
 */
@Singleton
open class ServerRepository @Inject constructor(
	private val serverDao: EntServerDao,
	private val serverInfoRepository: ServerInfoRepository,
) {
	
	/// list of all saved servers
	val serverInfo: Flow<List<ServerInfoData>> = serverInfoRepository.serverInfoList
	
	suspend fun addServer(
		hostname: String,
		port: Int = 5600,
		publicKey: PublicKey? = null
	): EntServer? {
		
		val info = serverInfoRepository.fetchInfo(hostname, port) ?: return null
		
		val server = EntServer(
			host = hostname,
			port = port,
			serverInfo = ServerInfo(
				name = info.name,
				owner = info.owner,
				alias = ""
			)
		)
		
		serverDao.insert(
			server
		)
		
		return server
	}
	
	suspend fun removeServer(uuid: UUID) {
		serverDao.deleteServer(uuid)
	}
	
	suspend fun refetch(hostname: String) {
		serverInfoRepository.refetch(hostname)
	}
	
	suspend fun findServer(hostname: String): ServerData? = serverDao.getServer(hostname)?.let {
		ServerData(
			uuid = it.uuid,
			hostname = it.host,
			port = it.port,
			name = it.serverInfo?.name ?: "unknown",
			owner = it.serverInfo?.owner ?: "unknown"
		)
	}

}
