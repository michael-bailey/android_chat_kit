package io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StickyHeaderServer(
	modifier: Modifier = Modifier,
	vm: DebugActivityServersViewModel,
	nav: NavController
) {
	
	Card(
		modifier = modifier
	) {
		Row(
			modifier = Modifier
				.padding(8.dp)
				.wrapContentHeight(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Absolute.spacedBy(16.dp)
		) {
			
			Text(
				text = "Servers"
			)
			
			IconButton(
				colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
				onClick = { nav.navigate("Add_Server") }
			) {
				Icon(Icons.Outlined.Add, contentDescription = "")
			}
		}
	}
}
