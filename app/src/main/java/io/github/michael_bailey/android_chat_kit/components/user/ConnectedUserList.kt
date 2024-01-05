package io.github.michael_bailey.android_chat_kit.components.user

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedUserViewModel

@Composable
fun ConnectedUserList(vm: IConnectedUserViewModel) {
	
	val users by vm.users.observeAsState(listOf())
	
	LazyColumn(modifier = Modifier
		.fillMaxWidth(0.9f)
		.fillMaxHeight()) {
		
		for (i in users) {
			item { Text(text = "User: ${i.username}") }
		}
	}
}