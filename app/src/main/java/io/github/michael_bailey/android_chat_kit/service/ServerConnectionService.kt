package io.github.michael_bailey.android_chat_kit.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.repository.ServerRepository

class ServerConnectionService(
	private val serverRepository: ServerRepository
) : Service() {

	override fun onCreate() {
		super.onCreate()
		log("onCreate")

		val t = Toast(this).apply {
			setText("Service Started")
		}

	}
	
	override fun onBind(intent: Intent?): IBinder? = null



	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
	}
	
	
}