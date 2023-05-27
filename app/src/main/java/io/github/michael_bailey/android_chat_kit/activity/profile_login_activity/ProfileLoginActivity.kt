package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme
import java.util.UUID

@AndroidEntryPoint
class ProfileLoginActivity: ComponentActivity() {
	
	private val vm: ProfileLoginViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		log("onCreate")
		
		setContent {
			val token by vm.recentToken.collectAsState(initial = null)
			if (token != null) {
				this.returnResult(token!!.first, token!!.second)
			}
			
			
			ChatKitAndroidTheme {
				
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Main(vm)
				}
			}
		}
	}

	/**
	 * returns profile details to the requesting activity
	 */
	private fun returnResult(uuid: UUID, password: String) {
		Intent().apply {
			// todo: TURN THIS INTO AN AUTH TOKEN SYSTEM
			putExtra("uuid", uuid)
			putExtra("password", password)
			setResult(RESULT_OK, this)
			finish()
		}
	}
}
