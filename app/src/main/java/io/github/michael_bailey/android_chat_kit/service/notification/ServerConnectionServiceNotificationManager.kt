package io.github.michael_bailey.android_chat_kit.service.notification

import android.app.Notification
import android.content.Context
import android.content.ContextWrapper
import io.github.michael_bailey.android_chat_kit.R
import io.github.michael_bailey.android_chat_kit.extension.context_wrapper.notificationManager
import io.github.michael_bailey.android_chat_kit.notifications.StaticNotificationChannels


class ServerConnectionServiceNotificationManager(
	val context: Context,
): ContextWrapper(context) {
	fun sendConnectingNotification(): Int {
		val notificationId = (Math.random() * 1000000).toInt()
		
		val notification = Notification.Builder(this, StaticNotificationChannels.GENERAL_CHANNEL_ID)
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.setContentTitle("ChatKit - Connecting")
			.setContentText("Please hold, we are getting you connected...")
			.setGroup("General")
			.setGroupAlertBehavior(Notification.GROUP_ALERT_SUMMARY)
			.build()
		
		notificationManager().notify(notificationId, notification)
		
		return notificationId
	}
	
	fun sendConnectedNotification(): Int {
		val notificationId = (Math.random() * 1000000).toInt()
		
		val notification = Notification.Builder(this, StaticNotificationChannels.GENERAL_CHANNEL_ID)
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.setContentTitle("ChatKit - Connected")
			.setContentText("Succesfully connected to the server, please enjoy!")
			.setGroup("General")
			.setGroupAlertBehavior(Notification.GROUP_ALERT_SUMMARY)
			.build()
		
		notificationManager().notify(notificationId, notification)
		
		return notificationId
	}
	
	// send error connecting notification
	fun sendErrorConnecting(reason: String) {
		val notification = Notification.Builder(this, StaticNotificationChannels.ERRORS_CHANNEL_ID)
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.setContentTitle("ChatKit - Error")
			.setContentText("An error occurred while connecting to the server: $reason")
			.setGroup("Errors")
			.setGroupAlertBehavior(Notification.GROUP_ALERT_SUMMARY)
			.build()
		
		notificationManager().notify((Math.random() * 1000000).toInt(), notification)
	}
}
