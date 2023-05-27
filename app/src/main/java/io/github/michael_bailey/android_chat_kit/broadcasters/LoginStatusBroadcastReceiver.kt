package io.github.michael_bailey.android_chat_kit.broadcasters

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.michael_bailey.android_chat_kit.utils.login.LoginStatus

class LoginStatusBroadcastReceiver(
	val onReceive: (Context?) -> Unit
): BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		val status = intent?.extras?.getParcelable("status", LoginStatus::class.java)
		onReceive(context)
	}
	
	companion object {
		const val KEY = "status"
		fun broadcast(ctx: Context, status: LoginStatus) {
			ctx.sendBroadcast(Intent().apply {
				putExtra(KEY, status)
			})
		}
	}
}