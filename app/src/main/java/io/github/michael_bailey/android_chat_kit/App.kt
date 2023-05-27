package io.github.michael_bailey.android_chat_kit

import android.app.Application
import android.app.NotificationManager
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.location.LocationManager
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import io.github.michael_bailey.android_chat_kit.extension.any.log
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

	private lateinit var locationService: LocationManager
	private lateinit var notificationService: NotificationManager
	private lateinit var biometricsService: BiometricManager

	internal lateinit var appDebugPreferencesManager: DebugPreferencesManager

	private fun createServiceIntent() = Intent(
		this,
		ServerConnectionService::class.java
	)

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

		appDebugPreferencesManager = DebugPreferencesManager(this)
//
//		val serviceintent = Intent(this, ServerConnectionService::class.java)
//
//		startService(serviceintent)

	}

	override fun onTerminate() {
		super.onTerminate()
		log("onTerminate: app terminated")

		stopService(createServiceIntent())
	}
}