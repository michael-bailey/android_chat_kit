package io.github.michael_bailey.android_chat_kit.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import io.github.michael_bailey.android_chat_kit.extension.any.log

class ServerConnectionService : Service() {

	override fun onCreate() {
		super.onCreate()
		log("onCreate")

		val t = Toast(this).apply {
			setText("Service Started")
		}

	}

	override fun onBind(intent: Intent): IBinder {
		TODO("Return the communication channel to the service.")
	}

	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
	}
}