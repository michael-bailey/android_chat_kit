package io.github.michael_bailey.android_chat_kit.extension.activity

import android.app.Activity
import android.content.Intent
import io.github.michael_bailey.android_chat_kit.activity.server_share_activity.ServerShareActivity
import io.github.michael_bailey.android_chat_kit.data_type.ServerData

fun Activity.startServerShareActivity(server: ServerData) {
	val intent = Intent(this, ServerShareActivity::class.java).apply {
		putExtra("data", server.into())
	}
	
	startActivity(intent)
}