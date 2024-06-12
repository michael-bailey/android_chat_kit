package io.github.michael_bailey.android_chat_kit.notifications

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager

object StaticNotificationChannels {
	
	const val MISC_GROUP_ID = "MISC_GROUP"
	
	const val GENERAL_CHANNEL_ID = "GENERAL"
	const val ERRORS_CHANNEL_ID = "ERRORS"
	
	
	private val notification_groups = mapOf(
		MISC_GROUP_ID to NotificationChannelGroup(MISC_GROUP_ID, "Miscellaneous"),
	)
	
	private val channels = listOf(
		NotificationChannel(GENERAL_CHANNEL_ID, "General", NotificationManager.IMPORTANCE_DEFAULT).apply { group = MISC_GROUP_ID },
		NotificationChannel(ERRORS_CHANNEL_ID, "Errors", NotificationManager.IMPORTANCE_HIGH).apply { group = MISC_GROUP_ID }
	)
	
	fun setup(notificationManager: NotificationManager) {
		notificationManager.createNotificationChannelGroups(notification_groups.values.toList())
		notificationManager.createNotificationChannels(channels)
	}
	
}