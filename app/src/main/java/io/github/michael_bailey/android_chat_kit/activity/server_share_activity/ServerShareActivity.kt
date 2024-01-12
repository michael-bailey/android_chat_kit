package io.github.michael_bailey.android_chat_kit.activity.server_share_activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.michael_bailey.android_chat_kit.activity.Activity
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

class ServerShareActivity : Activity<ServerShareActivityViewModel>() {
	
	override val vm by viewModels<ServerShareActivityViewModel>()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ChatKitAndroidTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					Greeting("Android")
				}
			}
		}
	}
}

