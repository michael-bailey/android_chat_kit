package io.github.michael_bailey.android_chat_kit.activity.home_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@AndroidEntryPoint
class HomeActivity: ComponentActivity() {
	
	private val vm: HomeActivityViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setContent {
			ChatKitAndroidTheme {
				Main(vm = vm)
			}
		}
	}
	
	fun refetchDetails(hostname: String) {
		vm.refetchDetails(hostname)
	}
}
