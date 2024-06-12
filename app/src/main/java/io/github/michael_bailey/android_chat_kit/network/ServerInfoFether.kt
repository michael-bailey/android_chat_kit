package io.github.michael_bailey.android_chat_kit.network

import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.extension.buffered_reader.genReadline
import io.github.michael_bailey.android_chat_kit.extension.buffered_writer.genWriteline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageOutput
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

object ServerInfoFether {
	
	/**
	 * fetches server information.
	 *
	 * TODO: add exception handling to this function.
	 */
	suspend fun fetchInfo(hostname: String, port: Int = 5600): ServerData? = withContext(Dispatchers.IO) {
		
		log("fetching info for $hostname:$port")
		
		var socket_res = kotlin.runCatching {
				Socket().apply {
					connect(InetSocketAddress(InetAddress.getByName(hostname), port), 1000)
				}
			}
		
		
		if (socket_res.isFailure) {
			log("failed to connect to $hostname:$port")
			return@withContext null
		}
		
		val socket = socket_res.getOrThrow()
		
		val input = withContext(Dispatchers.IO) {
			socket.getInputStream()
		}.bufferedReader()
		val output = withContext(Dispatchers.IO) {
			socket.getOutputStream()
		}.bufferedWriter()
		
		log("getting request command from $hostname:$port")
		val requestString = input.genReadline()
		val requestMsg = Json.decodeFromString<NetworkMessageOutput>(requestString)
		
		if (requestMsg !is NetworkMessageOutput.Request) {
			log("invalid response from $hostname:$port")
			return@withContext null
		}
		
		log("sending info command to $hostname:$port")
		val infoCommand = Json.encodeToString<NetworkMessageInput>(NetworkMessageInput.Info)
		
		output.genWriteline(infoCommand)
		
		val infoString  = input.genReadline()
		val infoMsg = Json.decodeFromString<NetworkMessageOutput>(infoString)
		
		
		if (infoMsg is NetworkMessageOutput.GotInfo) {
			log("got info from $hostname:$port")
			return@withContext ServerData(
				hostname = infoMsg.hostname,
				port = port,
				name = infoMsg.server_name,
				owner = infoMsg.server_owner,
			)
		}
		
		log("failed to get info from $hostname:$port")
		return@withContext null
	}
}