package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.componenets.ProfileLoginList
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme
import io.github.michael_bailey.android_chat_kit.theme.header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: AbstractProfileLoginViewModel) {

	val profiles by vm.profiles.observeAsState(listOf())

	Scaffold(
		topBar = { TopAppBar(title = { Text("Login") }) },
		content = {
			Surface(
				Modifier
					.padding(it)
					.fillMaxSize()) {
				Column(
					Modifier
						.fillMaxHeight()
						.fillMaxWidth(0.91f),
					Arrangement.SpaceEvenly,
					Alignment.CenterHorizontally,
				) {
					Text(text = "Profile Login", fontSize = header)
					if (profiles.isEmpty()) {
						Text("No Profiles Create one using the button below")
					} else {
						ProfileLoginList(Modifier.height(300.dp), profiles = profiles) { id, pass ->
							vm.login(uuid = id, password = pass)
						}
					}
					Button(
						modifier = Modifier
							.height(100.dp)
							.width(150.dp),
						shape = RoundedCornerShape(16.dp),
						onClick = { vm.create("name", "password") }
					) {
						Text("Add")
					}
				}
			}
		},
		bottomBar = {},
	)
}

@Preview
@Composable
fun profileMainView() {
	val vm = AbstractProfileLoginViewModel.PreviewVM
	ChatKitAndroidTheme {
		Main(vm)
	}
}