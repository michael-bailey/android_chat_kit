package io.github.michael_bailey.android_chat_kit.components.scopes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AltRoute
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController


class NavigationRailPageScaffoldScope(
): PageScaffoldScope<NavigationRailPageScaffoldScope.NavigationPageData>() {
	
	class NavigationPageData(
		route: String,
		val label: String,
		val icon: ImageVector,
		title: String? = null,
		composable: @Composable (NavController, PaddingValues?) -> Unit,
	): PageData(route = route, composable = composable, title = title)
	
	
	
	fun railPage(
		route: String,
		title: String? = null,
	 	icon: ImageVector = Icons.Outlined.AltRoute,
		label: String,
		composable: @Composable (NavController, PaddingValues?) -> Unit
	) {
		pages.add(
			NavigationPageData(
				route = route,
				label = label,
				title = title,
				icon = icon,
				composable = composable
			)
		)
	}
}