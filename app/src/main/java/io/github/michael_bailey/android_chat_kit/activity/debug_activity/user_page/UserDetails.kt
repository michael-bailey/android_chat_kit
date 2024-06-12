package io.github.michael_bailey.android_chat_kit.activity.debug_activity.user_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page.DebugActivityServersViewModel

@Composable
fun UserDetails(
	padding: PaddingValues,
	nav: NavController,
	scrollState: LazyListState,
	vm: DebugActivityServersViewModel
) {
	
	val vm = hiltViewModel<DebugActivityUserViewModel>()
	
	val userId by vm.userId.observeAsState(initial = "Loading...")
	val username by vm.username.observeAsState(initial = "Loading...")
	
	LazyColumn(
		modifier = Modifier
			.padding(padding)
			.fillMaxSize(),
		contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
		state = scrollState,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		
		item {
			OutlinedTextField(
				value = userId,
				onValueChange = vm::setUsername,
				label = {Text("User ID") },
				maxLines = 1,
				singleLine = true
			)
		}
		
		item {
			OutlinedTextField(
				value = username!!,
				onValueChange = vm::setUsername,
				label = {Text("Username") },
				maxLines = 1
			)
		}
	}
}
