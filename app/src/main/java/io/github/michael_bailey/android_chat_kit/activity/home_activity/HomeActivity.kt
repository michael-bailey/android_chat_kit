package io.github.michael_bailey.android_chat_kit.activity.home_activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.activity.server_activity.ServerActivity
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme
import java.util.UUID

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
	
	fun connectToServer(serverId: UUID) {
		val activityIntent = Intent(this, ServerActivity::class.java).apply {
			putExtra("server_id", serverId)
		}
		startActivity(activityIntent)
	}
	
	fun openDebugger() {
		startActivity(
			
			Intent().apply {
				action = "android.intent.action.VIEW"
				data = Uri.parse("chatkit://debug.settings/")
			}
		)
	}
}
