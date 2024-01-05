package io.github.michael_bailey.android_chat_kit.extension.activity

import android.app.Activity
import android.content.Intent
import io.github.michael_bailey.android_chat_kit.activity.server_activity.ServerActivity

fun Activity.startServerConnectionActivity(
	hostname: String,
	port: Int,
) {
	
	val intent = Intent(this, ServerActivity::class.java).apply {
		putExtra("hostname", hostname)
		putExtra("port", port)
	}
	
	startActivity(intent)
	
}

