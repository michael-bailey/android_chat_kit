package io.github.michael_bailey.android_chat_kit.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.repository.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ServerConnectionService(
) : Service() {
	
	private val job = SupervisorJob()
	private val serviceScope = CoroutineScope(Dispatchers.Main + job)
	
	private lateinit var serverRepository: ServerRepository
	
	override fun onCreate() {
		super.onCreate()
		
		serverRepository = ServerRepository()
	}
	
	override fun onBind(intent: Intent?): IBinder? = null



	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
	}
	
	class ServerConnectionBinder(
	
	): Binder() {
	
	}
}