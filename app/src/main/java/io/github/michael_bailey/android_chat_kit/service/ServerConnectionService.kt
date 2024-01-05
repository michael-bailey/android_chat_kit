package io.github.michael_bailey.android_chat_kit.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.github.michael_bailey.android_chat_kit.repository.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class ServerConnectionService @Inject constructor(
	private var serverRepository: ServerRepository
) : Service() {
	
	private val job = SupervisorJob()
	private val serviceScope = CoroutineScope(Dispatchers.Default + job)
	
	override fun onBind(intent: Intent?): IBinder = ServerConnectionBinder()
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		return super.onStartCommand(intent, flags, startId)
	}
	
	class ServerConnectionBinder(
	
	): Binder() {
	
	}
}