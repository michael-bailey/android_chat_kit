package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.contract

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.ProfileLoginActivity
import java.util.UUID

class ProfileLoginContract: ActivityResultContract<Unit, Pair<UUID, String>?>() {
	override fun createIntent(context: Context, input: Unit): Intent =
		Intent(context, ProfileLoginActivity::class.java)

	override fun parseResult(
		resultCode: Int,
		intent: Intent?
	): Pair<UUID, String>? {

		return when (resultCode) {
			RESULT_OK -> intent?.let {
				it.getSerializableExtra(
					"uuid",
					UUID::class.java
				)!! to it.getStringExtra("password")!!
			}
			else -> null
		}
	}






}