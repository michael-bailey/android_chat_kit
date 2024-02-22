package io.github.michael_bailey.android_chat_kit.components.scopes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.components.configs.FABData


class BottomNavigationPageScaffoldScope(
): PageScaffoldScope<BottomNavigationPageScaffoldScope.NavigationPageData>() {
	
	class NavigationPageData(
		route: String,
		val label: String,
		val icon: ImageVector,
		title: String? = null,
		val fab: FABData? = null,
		composable: @Composable (NavController, PaddingValues?) -> Unit,
	): PageData(route = route, composable = composable, title = title)
	
	
	
	fun bottomPage(
		route: String,
		title: String? = null,
	 	icon: ImageVector,
		label: String,
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
				composable = composable
			)
		)
	}
}