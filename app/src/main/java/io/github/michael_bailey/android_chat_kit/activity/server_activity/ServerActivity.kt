package io.github.michael_bailey.android_chat_kit.activity.server_activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.activity.Activity
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@AndroidEntryPoint
class ServerActivity: Activity<ServerActivityViewModel>() {
	
	override val vm by viewModels<ServerActivityViewModel>()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setContent {
			ChatKitAndroidTheme {
				Main(vm = vm)
			}
		}
	}
}

