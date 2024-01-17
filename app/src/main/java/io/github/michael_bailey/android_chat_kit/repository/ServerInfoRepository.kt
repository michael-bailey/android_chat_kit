package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.embed.ServerInfo
import io.github.michael_bailey.android_chat_kit.extension.buffered_reader.genReadline
import io.github.michael_bailey.android_chat_kit.extension.buffered_writer.genWriteline
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageOutput
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

/**
 * this stores info on all hosts added
 *
 * ## Future Work
 * - add ability to refresh information after a time
 */
@Singleton
class ServerInfoRepository @Inject constructor(
	private val serverDao: EntServerDao,
): Repository() {
	
	private val _serverHostMap = serverDao.genServers().map { list ->
		list.associateBy { it.host }
	}
	
	private val _serverUUIDMap = serverDao.genServers().map { list ->
		list.associateBy { it.uuid }
	}
	
	val serverInfoList = serverDao.genServers().map { list ->
		list.map {
			ServerData(
				uuid = it.uuid,
				hostname = it.host,
				port = it.port,
				name = it.serverInfo?.name ?: "null",
				owner = it.serverInfo?.owner ?: "null"
			)
		}
	}
	
	val serverHostInfo = _serverHostMap.map { map ->
		map.map { (k,v) -> k to v.serverInfo }.toMap()
	}
	
	val serverUUIDInfo =  _serverUUIDMap.map { map ->
		map.map { (k,v) -> k to v.serverInfo }.toMap()
	}
	
	suspend fun getInfo(hostname: String, port: Int = 5600): ServerData? {
		val server = serverDao.getServer(hostname)
		
		if (server != null) {
			return ServerData(
				uuid = server.uuid,
				hostname = server.host,
				port = server.port,
				name = server.serverInfo?.name ?: "null",
				owner = server.serverInfo?.owner ?: "null"
			)
		} else {

			return fetchInfo(hostname, port)
		}
	}
	
	/**
	 * fetches server information.
	 *
	 * TODO: add exception handling to this function.
	 */
	suspend fun fetchInfo(hostname: String, port: Int = 5600): ServerData? {
		
		val socket = Socket(hostname, port)
		
		val input = socket.getInputStream().bufferedReader()
		val output = socket.getOutputStream().bufferedWriter()
		
		val requestString = input.genReadline()
		val requestMsg = Json.decodeFromString<NetworkMessageOutput>(requestString)
		
		if (requestMsg !is NetworkMessageOutput.Request) {
			return null
		}
		
		val infoCommand = Json.encodeToString<NetworkMessageInput>(NetworkMessageInput.Info)
		
		output.genWriteline(infoCommand)
		
		val infoString  = input.genReadline()
		val infoMsg = Json.decodeFromString<NetworkMessageOutput>(infoString)
		
		if (infoMsg is NetworkMessageOutput.GotInfo) {
			return ServerData(
				hostname = infoMsg.hostname,
				port = port,
				name = infoMsg.server_name,
				owner = infoMsg.server_owner,
			)
		}
		
		return null
	}
	
	suspend fun refetch(hostname: String) {
		val info = fetchInfo(hostname)
		val server = serverDao.getServer(hostname)
		
		info?.also {
			server?.run {
				this.serverInfo = ServerInfo(
					name = it.name,
					owner = it.owner,
					alias = ""
				)
				
				serverDao.updateServer(this)
			}
		}
	}
}