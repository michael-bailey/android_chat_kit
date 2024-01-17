package io.github.michael_bailey.android_chat_kit.extension.activity

import android.app.Activity
import android.content.Intent


fun Activity.startShareSheet(text: String) {
	val sendIntent: Intent = Intent().apply {
		action = Intent.ACTION_SEND
		putExtra(Intent.EXTRA_TEXT, text)
		type = "text/plain"
	}
	
	val shareIntent = Intent.createChooser(sendIntent, null)
	startActivity(shareIntent)
}