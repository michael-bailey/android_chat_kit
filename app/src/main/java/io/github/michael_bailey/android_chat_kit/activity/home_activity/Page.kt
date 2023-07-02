package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.michael_bailey.android_chat_kit.activity.home_activity.chat_page.ChatPage
import io.github.michael_bailey.android_chat_kit.activity.home_activity.home_page.HomePage
import io.github.michael_bailey.android_chat_kit.activity.home_activity.server_page.ServerPage
import io.github.michael_bailey.android_chat_kit.extension.any.log

sealed class Page(
	val route: String,
	@StringRes val label: Int? = null,
	val icon: ImageVector,
	val pageFunction: @Composable ((HomeViewModel) -> Unit)
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
	
	fun compose(it: NavGraphBuilder, vm: HomeViewModel) {
		it.composable(route) {
			pageFunction(vm)
		}
	}
	
	@Composable
	fun RowScope.NavItem(nav: NavHostController) {
		val backstack by nav.currentBackStackEntryAsState()
		val isSelected = backstack?.destination?.hierarchy?.any{ it.route == route } == true
		
		log("nav destination: ${nav.currentDestination?.route}")
		
		NavigationBarItem(
			selected = isSelected,
			onClick = { navigate(nav) },
			icon = { Icon() },
			label = { Label() }
		)
	}
	
	@Composable
	fun Label() {
		Text(label?.let { stringResource(id = it) } ?: route)
	}
	
	@Composable
	fun Icon() {
		androidx.compose.material.Icon(
			imageVector = icon,
			contentDescription = "A Home Button For The App"
		)
	}
	
	private fun navigate(nav: NavHostController) {
		nav.navigate(this.route) {
			popUpTo(nav.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}
}
