package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.michael_bailey.android_chat_kit.components.configs.BottomBarData
import io.github.michael_bailey.android_chat_kit.components.messaging.GlobalChatListView
import io.github.michael_bailey.android_chat_kit.components.messaging.MessageSenderBar
import io.github.michael_bailey.android_chat_kit.components.messaging.UserChatListView
import io.github.michael_bailey.android_chat_kit.components.navigation.NavigationDrawPageScaffold
import io.github.michael_bailey.android_chat_kit.theme.drawerHeader

@Composable
fun Main() {
	
	val vm = (LocalContext.current as ServerActivity).getViewModel()
	
	val selfId by vm.userId.observeAsState()
	val serverHostname by vm.serverHostname.observeAsState("")
	val serverName by vm.serverName.observeAsState("")
	val serverOwner by vm.serverOwner.observeAsState("")
	
	val users by vm.users.observeAsState(listOf())
	val usersDiscludeSelf by remember {
		derivedStateOf {
			users.filter { it.uuid != selfId }
		}
	}
	
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
		) { nav, pad ->
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				val msgs by vm.globalMessages.observeAsState(initial = listOf())
				GlobalChatListView(msgs)
			}
		}
		
		for (i in usersDiscludeSelf) {
			drawerPage(
				route = i.uuid.toString(),
				icon = Icons.Outlined.Person,
				label = i.name,
				title = i.name,
				bottomBar = BottomBarData {
					MessageSenderBar {
						vm.sendUserMessage(i.uuid, it)
						true
					}
				}
			) { nav, pad ->
				
				val messageListFlow by vm.getUserMessageStore(i.uuid).observeAsState(listOf())
				
				
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					UserChatListView(messageListFlow)
				}
			}
		}
	}
}
