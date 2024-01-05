package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.michael_bailey.android_chat_kit.components.configs.BottomBarData
import io.github.michael_bailey.android_chat_kit.components.messaging.ChatListView
import io.github.michael_bailey.android_chat_kit.components.messaging.MessageSenderBar
import io.github.michael_bailey.android_chat_kit.components.navigation.NavigationDrawPageScaffold
import io.github.michael_bailey.android_chat_kit.components.user.ConnectedUserList
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedServerViewModel
import io.github.michael_bailey.android_chat_kit.theme.drawerHeader

@Composable
fun Main(vm: IConnectedServerViewModel) {
	
	val serverHostname by vm.serverHostname.observeAsState("")
	
	val serverName by vm.serverName.observeAsState("")
	val serverOwner by vm.serverOwner.observeAsState("")
	
	val users by vm.users.observeAsState(listOf())
	
	
	NavigationDrawPageScaffold(
		startRoute = "global_chat",
		drawerHeader = {
			Text(text = serverName, fontSize = drawerHeader)
			Text(text = serverOwner)
			Text(text = serverName)
		}
	) {
		
		drawerPage(
			route = "global_chat",
			icon = Icons.Outlined.Person,
			label = "Global Chat",
			title = "Global Chat",
			bottomBar = BottomBarData {
				MessageSenderBar(vm)
			}
		) { nav, pad ->
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				ChatListView(vm = vm)
			}
		}
		
		for (i in users) {
			drawerPage(
				route = i.uuid.toString(),
				icon = Icons.Outlined.Person,
				label = i.username,
				title = i.username,
			) { nav, pad ->
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					ConnectedUserList(vm = vm)
				}
			}
		}
	}
}
