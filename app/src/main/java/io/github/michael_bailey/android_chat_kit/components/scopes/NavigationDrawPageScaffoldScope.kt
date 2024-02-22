package io.github.michael_bailey.android_chat_kit.components.scopes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.components.configs.BottomBarData
import io.github.michael_bailey.android_chat_kit.components.configs.FABData


class NavigationDrawPageScaffoldScope(
): PageScaffoldScope<NavigationDrawPageScaffoldScope.NavigationPageData>() {
	
	class NavigationPageData(
		route: String,
		val label: String,
		val icon: ImageVector,
		title: String? = null,
		val bottomBar: BottomBarData? = null,
		val fab: FABData? = null,
		composable: @Composable (NavController, PaddingValues?) -> Unit,
	): PageData(route = route, composable = composable, title = title)
	
	class HeaderData(
		title: String
	)
	
	fun drawerPage(
		route: String,
		label: String,
		icon: ImageVector,
		title: String? = null,
		bottomBar: BottomBarData? = null,
		fab: FABData? = null,
		composable: @Composable (NavController, PaddingValues?) -> Unit
	) {
		pages.add(
			NavigationPageData(
				route = route,
				label = label,
				title = title,
				icon = icon,
				fab = fab,
				bottomBar = bottomBar,
				composable = composable
			)
		)
	}
	
	fun header() {
	
	}
}