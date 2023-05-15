package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@Composable
fun Main(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
	ChatKitAndroidTheme {
		Main("Android")
	}
}