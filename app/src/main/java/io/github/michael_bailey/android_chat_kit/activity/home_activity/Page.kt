package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.michael_bailey.android_chat_kit.activity.home_activity.chat_page.ChatPage
import io.github.michael_bailey.android_chat_kit.activity.home_activity.home_page.HomePage
import io.github.michael_bailey.android_chat_kit.activity.home_activity.server_page.ServerPage
import io.github.michael_bailey.android_chat_kit.utils.page.FabConfig
import io.github.michael_bailey.android_chat_kit.utils.page.PageNavigation

sealed class Page(
	route: String,
	label: Int? = null,
	icon: ImageVector,
	pageFunction: @Composable ((HomeViewModel) -> Unit),
	fab: FabConfig<HomeViewModel>? = null
): PageNavigation<HomeViewModel>(
	route,
	label,
	icon,
	pageFunction,
) {
	companion object {
		object Home: Page(
			"home",
			icon = Icons.Outlined.Home,
			pageFunction = { HomePage(vm = it) }
		)
		object Chat: Page(
			"chat",
			icon = Icons.Outlined.Send,
			pageFunction = { ChatPage(vm = it)}
		)
		object Server: Page(
			"server",
			icon = Icons.Outlined.List,
			pageFunction = { ServerPage(it) }
		)
	}
}
