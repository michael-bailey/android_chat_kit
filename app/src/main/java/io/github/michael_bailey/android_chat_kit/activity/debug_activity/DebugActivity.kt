package io.github.michael_bailey.android_chat_kit.activity.debug_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.activity.Activity
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page.DebugActivityServersViewModel
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionService
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@AndroidEntryPoint
class DebugActivity() : Activity<DebugActivityViewModel>() {
	
	companion object {
		val URI = "chatkit://debug.settings"
	}
	
	override val viewModel: DebugActivityViewModel by viewModels()
	
	private val serverViewModel: DebugActivityServersViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		bindService(Intent(this, ServerConnectionService::class.java), viewModel, Context.BIND_AUTO_CREATE)
		bindService(Intent(this, ServerConnectionService::class.java), serverViewModel, Context.BIND_AUTO_CREATE)
		
		setContent {
			ChatKitAndroidTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					Main(viewModel = viewModel, serverViewModel = serverViewModel)
				}
			}
		}
	}
}