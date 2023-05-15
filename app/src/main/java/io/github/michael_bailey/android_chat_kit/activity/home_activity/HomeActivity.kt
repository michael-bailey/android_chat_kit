package io.github.michael_bailey.android_chat_kit.activity.home_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.room.Room
import io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.contract.ProfileLoginContract
import io.github.michael_bailey.android_chat_kit.database.AppDatabase
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

class HomeActivity : ComponentActivity() {

	val activityLauncher = registerForActivityResult(ProfileLoginContract()) { result ->
		vm.setProfileToken(result)
	}

	private lateinit var vm: HomeViewModel
	private lateinit var db: AppDatabase

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		db = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java,
			"gym_log_book_db"
		).build()

		val vm: HomeViewModel by viewModels {
			HomeViewModel.Factory({ activityLauncher.launch(Unit) }, db.profileDao())
		}
		this.vm = vm

		setContent {
			ChatKitAndroidTheme {

				val tokenState by vm.loggedInUser.observeAsState()
				if (tokenState == null) {
					Text(text = "Not Logged In")
					return@ChatKitAndroidTheme
				}


				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Main("Android")
				}
			}
		}
	}
}
