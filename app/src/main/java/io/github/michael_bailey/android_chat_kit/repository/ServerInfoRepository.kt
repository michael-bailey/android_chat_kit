package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.data_type.ServerData.Companion.ServerConnectionStatus
import io.github.michael_bailey.android_chat_kit.database.embed.ServerInfo
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.network.ServerInfoFether
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.toKotlinDuration

/**
 * This fetches and presents Servers Information
 *
 * Wraps ServerRepository to fetch and return [ServerData]
 * Refetches [ServerInfo] whilst in use
 */
@Singleton
class ServerInfoRepository @Inject constructor(
	private val serverRepository: MutableServerRepository,
): Repository() {
	
	/**
	 * Re-fetches all Servers, every 5 seconds.
	 */
	private val infoFetcherJob: MutableStateFlow<Job?> = MutableStateFlow(null)
	
	private val currentTime = flow<LocalDateTime> {
		while (true) {
			emit(LocalDateTime.now())
			delay(Duration.ofSeconds(1).toKotlinDuration())
		}
	}
	
	val servers = serverRepository.allServers.combine(currentTime) { servers, now ->
		servers.map { server ->
			ServerData(
				uuid = server.uuid,
				hostname = server.host,
				port = server.port,
				name = server.serverInfo?.name ?: "Unknown",
				owner = server.serverInfo?.owner ?: "Unknown",
				status = getStatus(server, now),
			)
		}
	}
	
	init {
		startFetching()
	}
	
	/**
	 * gets updates for the server of a given ID
	 */
	fun genServerInfo(serverId: UUID) = serverRepository.flowServer(serverId).combine(currentTime) { server, now ->
		server?.run {
			ServerData(
				uuid = uuid,
				hostname = host,
				port = port,
				name = serverInfo?.name ?: "Unknown",
				owner = serverInfo?.owner ?: "Unknown",
				status = getStatus(this, now),
			)
		}
	}
	
	
	/**
	 * Refetches the server with the given hostname.
	 * Returns if could not connect to server.
	 */
	private suspend fun refetch(id: UUID) {
		val server = serverRepository.getServer(id) ?: return
		log("refetching server ${server.host}")
		val info = ServerInfoFether.fetchInfo(server.host, server.port) ?: return
		
		log("${server.host} is online")
		
		log("${server.host} previous update time: ${server.serverInfo?.updatedTime}")
		
		
		val serverInfo = ServerInfo(
			name = info.name,
			owner = info.owner,
			alias = server.serverInfo?.alias ?: ""
		)
		
		val newServer = server.copy(
			serverInfo = serverInfo
		)
		
		log("${server.host} new update time: ${newServer.serverInfo?.updatedTime}")
		
		serverRepository.updateServer(newServer)
	}
	
	fun getStatus(server: EntServer, now: LocalDateTime) = if (
		server.serverInfo == null
	) {
		ServerConnectionStatus.Unknown
	} else if(
		Duration.between(server.serverInfo?.updatedTime!!, now) < Duration.ofSeconds(6)
	) {
		ServerConnectionStatus.Online
	} else {
		ServerConnectionStatus.Offline
	}
	
	suspend fun getServer(serverId: UUID) = serverRepository.getServer(serverId)?.run {
		ServerData(
			uuid = uuid,
			hostname = host,
			port = port,
			name = serverInfo?.name ?: "Unknown",
			owner = serverInfo?.owner ?: "Unknown",
			status = getStatus(this, LocalDateTime.now()),
		)
	}
	
	fun startFetching() {
		infoFetcherJob.value = scope.launch(Dispatchers.IO) {
			while (this.isActive) {
				serverRepository.getAllServerIds().map { id ->
					scope.launch { refetch(id) }
				}.joinAll()
				delay(Duration.ofSeconds(5).toKotlinDuration())
			}
		}
	}
	
	fun stopFetching() {
		infoFetcherJob.value?.cancel()
		infoFetcherJob.value = null
	}
	
}