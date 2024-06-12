package io.github.michael_bailey.android_chat_kit.activity.server_activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.activity.Activity
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionService
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme
import java.util.UUID

@AndroidEntryPoint
class ServerActivity(
): Activity<ServerActivityViewModel>() {
	
	override val viewModel by viewModels<ServerActivityViewModel>()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		val server_id = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			intent.getSerializableExtra("server_id", UUID::class.java)
		} else {
			intent.getSerializableExtra("server_id") as UUID
		}
		
		if (server_id == null) {
			finish()
		}
		
		bindService(
			Intent(this, ServerConnectionService::class.java),
			this.viewModel,
			Context.BIND_AUTO_CREATE
		)
		
		
		setContent {
			ChatKitAndroidTheme {
				Main()
			}
		}
	}
}

