package io.github.michael_bailey.android_chat_kit.activity.connected_server_activity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.michael_bailey.android_chat_kit.activity.connected_server_activity.group_page.GroupsPage
import io.github.michael_bailey.android_chat_kit.activity.connected_server_activity.users_page.UsersPage
import io.github.michael_bailey.android_chat_kit.utils.page.PageNavigation


sealed class Page(
	route: String,
	label: Int?  = null,
	icon: ImageVector,
	pageFunction: @Composable (ConnectedServerViewModel) -> Unit
) : PageNavigation<ConnectedServerViewModel>(
	route,
	label,
	icon,
	pageFunction,
) {
	companion object {
		object Users: Page(
			"users",
			icon = Icons.Outlined.Person,
			pageFunction = { UsersPage(vm = it) }
		)
		object Groups: Page(
			"groups",
			icon = Icons.Outlined.List,
			pageFunction = { GroupsPage(vm = it) }
		)
	}
}
