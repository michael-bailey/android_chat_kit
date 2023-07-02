package io.github.michael_bailey.android_chat_kit.activity.thread_activity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	ChatKitAndroidTheme {
		Greeting("Android")
	}
}