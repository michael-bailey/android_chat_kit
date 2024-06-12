package io.github.michael_bailey.android_chat_kit.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.extension.buffered_writer.genWriteline
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerStateRepository
import io.github.michael_bailey.android_chat_kit.repository.UserListRepository
import io.github.michael_bailey.android_chat_kit.service.notification.ServerConnectionServiceNotificationManager
import io.github.michael_bailey.android_chat_kit.service.state.IServerConnectionStateDelegate
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionState
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateConnected
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateConnecting
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateDisconnected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageInput
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageOutput
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.util.UUID
import javax.inject.Inject
import kotlin.properties.Delegates.observable

/**
 * # ServerConnectionService
 * This Service controls connections to the chat server
 *
 * It is sent commands by the binder, all commands are run on the services coroutine scope
 *
 * It utilises the same repositories as activities.
 * so any state update made by teh service will be reflected in the UI
 */
@AndroidEntryPoint
class ServerConnectionService @Inject constructor(
): Service(), IServerConnectionStateDelegate {
	
	// repositories
	@Inject lateinit var loginRepository: LoginRepository
	@Inject lateinit var serverRepository: ServerInfoRepository
	@Inject lateinit var userListRepository: UserListRepository
	@Inject lateinit var messageRepository: MessageRepository
	@Inject lateinit var serverStateRepository: ServerStateRepository
	
	val notificationManager = ServerConnectionServiceNotificationManager(
		this
	)
	
	// coroutine stuff
	private val supervisorJob = SupervisorJob()
	private val scope = CoroutineScope(Dispatchers.IO + supervisorJob)
	
	// binder
	private val binder: ServerConnectionServiceBinder by lazy {
		ServerConnectionServiceBinder(this)
	}
	
	// service state
	private var state: ServerConnectionState by observable(
		ServerConnectionStateDisconnected
	) { _, _, new ->
		scope.launch { serverStateRepository.onStateChange(new) }
	}
	
	// mark: Service lifecycle
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		log("got new intent")
		// this intent should stay started
		// if there is an intent sent to this, it will not be redelivered
		return START_STICKY
	}
	
	override fun onBind(intent: Intent): IBinder {
		log("binding to caller")
		return binder
	}
	
	// mark: feature commands
	
	fun connect(serverId: UUID) = scope.launch(Dispatchers.IO) {
		if (state !is ServerConnectionStateDisconnected) {
			log("[connect]: Already connected, or Connecting")
			return@launch
		}
		
		val server = serverRepository.getServer(serverId) ?: return@launch
		
		(state as ServerConnectionStateDisconnected).connect(
			server = server,
			uuid = loginRepository.getUUID()!!,
			username = loginRepository.getUsername(),
		)
	}
	
	private fun initiateConnection() = scope.launch {
		
		val state = state as ServerConnectionStateConnecting
		
		val host = state.data.hostname
		val port = state.data.port
		
		val socketRes = kotlin.runCatching {
			Socket().apply {
				val address = InetAddress.getByName(host)
				connect(InetSocketAddress(address, port), 1000)
			}
		}
		
		if (socketRes.isFailure) {
			notificationManager.sendErrorConnecting("failed connecting socket")
			setDisconnected()
			return@launch
		}
		
		val socket = socketRes.getOrThrow()
		
		val input = socket.getInputStream().bufferedReader()
		val output = socket.getOutputStream().bufferedWriter()
		
		val requestString = input.readLine()
		val request = Json.decodeFromString<NetworkMessageOutput>(requestString)
		
		if (request !is NetworkMessageOutput.Request) {
			notificationManager.sendErrorConnecting("invalid opening response")
			setDisconnected()
			socket.close()
			return@launch
		}
		
		val connectRequestString = Json.encodeToString(
			serializer = NetworkMessageInput.serializer(),
			value = NetworkMessageInput.Connect(
				uuid = loginRepository.getUUID()!!,
				username = loginRepository.getUsername(),
				address = "127.0.0.1"
			)
		)
		
		output.genWriteline(connectRequestString)
		
		val connectedString = input.readLine()
		val connected = Json.decodeFromString<ClientMessageOutput>(connectedString)
		
		if (connected !is ClientMessageOutput.Connected) {
			notificationManager.sendErrorConnecting("invalid connected response, didn't get 'connected' message")
			setDisconnected()
			socket.close()
			return@launch
		}
		
		setConnected(state.data, socket)
		
		notificationManager.sendConnectedNotification()
	}
	
	fun disconnect() = scope.launch(Dispatchers.IO) {
		messageRepository.clear()
		userListRepository.clear()
		
		(state as ServerConnectionStateConnected).sendDisconnect()
		setDisconnected()
	}
	
	private fun setDisconnected() {
		state = ServerConnectionStateDisconnected
	}
	
	private fun setConnecting(
		server: ServerData,
	) {
		state = ServerConnectionStateConnecting(
			data = server,
			notificationId = notificationManager.sendConnectingNotification()
		)
	}
	
	private fun setConnected(
		server: ServerData,
		socket: Socket
	) {
		state = ServerConnectionStateConnected(
			serverData = server,
			socket = socket,
			messageRepository = messageRepository,
			userListRepository = userListRepository,
		)
	}
	
	// connection delegate methods
	override fun onConnecting(delegate: IServerConnectionStateDelegate) {
		TODO("Not yet implemented")
	}
	
	override fun onConnectingFailed(delegate: IServerConnectionStateDelegate) {
		TODO("Not yet implemented")
	}
	
	override fun onConnected(delegate: IServerConnectionStateDelegate) {
		TODO("Not yet implemented")
	}
	
	override fun onConnectionFailed(delegate: IServerConnectionStateDelegate) {
		TODO("Not yet implemented")
	}
	
	override fun onDisconnected(delegate: IServerConnectionStateDelegate) {
		TODO("Not yet implemented")
	}
}