package io.github.michael_bailey.android_chat_kit.activity.home_activity.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IProfileViewModel

@Composable
fun ProfilePage(
	vm: IProfileViewModel,
	nav: NavController,
	padding: PaddingValues
) {
	
	val uuid by vm.uuid.observeAsState("")
	val username by vm.username.observeAsState("")
	
	Column {
		Text(text = "username: $username")
		Text(text = "uuid: $uuid")
	}
}