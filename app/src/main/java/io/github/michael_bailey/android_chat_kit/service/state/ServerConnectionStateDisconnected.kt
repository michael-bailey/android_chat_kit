package io.github.michael_bailey.android_chat_kit.service.state

import android.content.Context
import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionService
import io.github.michael_bailey.android_chat_kit.service.notification.ServerConnectionServiceNotificationManager
import java.util.UUID

class ServerConnectionStateDisconnected(
	private val ctx: Context,
	delegate: IServerConnectionStateDelegate
): ServerConnectionState(delegate = delegate) {
	
	private val notifications = ServerConnectionServiceNotificationManager(ctx)
	
	constructor(service: ServerConnectionService): this(ctx = service, delegate = service)

	fun connect(
		server: ServerData,
		uuid: UUID,
		username: String,
	) {
		
		val notificationId = notifications.sendConnectingNotification()
		
		val nextState = ServerConnectionStateConnecting(
			data = server,
			notificationId = notificationId
		)
		
		delegate.onConnecting(nextState)
	}
	
}