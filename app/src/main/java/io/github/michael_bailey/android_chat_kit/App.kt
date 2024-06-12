package io.github.michael_bailey.android_chat_kit

import android.app.Application
import android.app.NotificationManager
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.location.LocationManager
import androidx.core.content.getSystemService
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.notifications.StaticNotificationChannels
import io.github.michael_bailey.android_chat_kit.preferences.DebugPreferencesManager
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionService


/**
 * # Application
 * Main applicaiton for the chat app for andorid.
 *
 * stores common system services and data models
 */
@HiltAndroidApp
class App(
): Application() {
	
	private val service_intents by lazy {
		listOf(
			Intent(this, ServerConnectionService::class.java)
		)
	}

	private lateinit var locationService: LocationManager
	private lateinit var notificationService: NotificationManager
	private lateinit var biometricsService: BiometricManager
	private lateinit var notificationManager: NotificationManager

	internal lateinit var appDebugPreferencesManager: DebugPreferencesManager
	
	override fun onCreate() {
		super.onCreate()
		log("onCreate")

		DynamicColors.applyToActivitiesIfAvailable(this)

		this.locationService = this.baseContext
			.getSystemService(LOCATION_SERVICE) as LocationManager
		this.notificationService = this.baseContext
			.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		this.biometricsService = this.baseContext
			.getSystemService(BIOMETRIC_SERVICE) as BiometricManager
		
		this.notificationManager = this.baseContext.getSystemService<NotificationManager>()!!

		appDebugPreferencesManager = DebugPreferencesManager(this)
		
		StaticNotificationChannels.setup(this.notificationManager)
		
		service_intents.forEach {
			startService(it)
		}
	}

	override fun onTerminate() {
		super.onTerminate()
		log("onTerminate: app terminated")
	}
}