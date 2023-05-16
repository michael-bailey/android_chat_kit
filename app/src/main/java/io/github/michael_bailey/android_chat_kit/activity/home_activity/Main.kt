package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(name: String, modifier: Modifier = Modifier) {
	Scaffold(
		topBar = { TopAppBar(title = { Text(text = "Header")}) },
		content = {
			Surface(modifier = Modifier.padding(it)) {
				Text(
					text = "Hello $name!",
					modifier = modifier
				)
			}
		}
	)
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
	ChatKitAndroidTheme {
		Main("Android")
	}
}