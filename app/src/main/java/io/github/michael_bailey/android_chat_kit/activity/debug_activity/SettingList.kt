package io.github.michael_bailey.android_chat_kit.activity.debug_activity

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SignalWifi4Bar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingList(
	padding: PaddingValues,
	nav: NavController,
	scrollState: LazyListState
) {
	LazyColumn(
		modifier = Modifier.fillMaxSize().padding(padding),
		state = scrollState,
		contentPadding = PaddingValues(20.dp)
	) {
		item {
			SettingsPanelButton(
				icon = Icons.Outlined.SignalWifi4Bar,
				name = "Servers",
				description = "Server configuration, And Management",
				nav = nav,
				route = "Servers"
			)
		}
		item {
			SettingsPanelButton(
				icon = Icons.Outlined.Person,
				name = "User Details",
				description = "User information, and Management",
				nav = nav,
				route = "User"
			)
		}
	}
}