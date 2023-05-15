package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import io.github.michael_bailey.android_chat_kit.database.AppDatabase
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.service.ServerConnectionService
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme
import java.util.UUID

class ProfileLoginActivity : ComponentActivity() {

	private lateinit var db: AppDatabase

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		log("onCreate")

		val serviceIntent = Intent(this, ServerConnectionService::class.java)

		startService(serviceIntent)

		db = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java,
			"gym_log_book_db"
		).build()

		val vm: ProfileLoginViewModel by viewModels() {
			ProfileLoginViewModel.Factory(db.profileDao(), this::returnResult)
		}

		setContent {
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
	fun returnResult(uuid: UUID, password: String) {
		Intent().apply {
			// todo: TURN THIS INTO AN AUTH TOKEN SYSTEM
			putExtra("uuid", uuid)
			putExtra("password", password)
			setResult(RESULT_OK, this)
			finish()
		}
	}
}
