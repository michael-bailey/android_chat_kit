package io.github.michael_bailey.android_chat_kit.activity.home_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.contract.ProfileLoginContract
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@AndroidEntryPoint
class HomeActivity: ComponentActivity() {
	
	private val activityLauncher: ActivityResultLauncher<Unit> = registerForActivityResult(ProfileLoginContract()) { result ->
		if (result == null) {
			this.launchLoginActivity()
		}
		vm.setProfileToken(result)
	}
	
	private val vm: HomeViewModel by viewModels()
	
	private fun launchLoginActivity() {
		this.activityLauncher.launch(Unit)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setContent {
			ChatKitAndroidTheme {
				val isLoggedIn by vm.isLoggedIn.observeAsState()
				when (isLoggedIn) {
					null -> {
						Text(text = "Loading")
					}
					false -> {
						Text(text = "Not Logged In")
						activityLauncher.launch(Unit)
						return@ChatKitAndroidTheme
					}
					true -> {
						Surface(
							modifier = Modifier.fillMaxSize(),
							color = MaterialTheme.colorScheme.background
						) {
							Main(vm)
						}
					}
				}
			}
		}
	}
}
