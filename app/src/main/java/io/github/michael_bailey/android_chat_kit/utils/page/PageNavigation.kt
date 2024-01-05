package io.github.michael_bailey.android_chat_kit.utils.page

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.michael_bailey.android_chat_kit.extension.any.log

abstract class PageNavigation<VM: ViewModel>(
	val route: String,
	@StringRes val label: Int? = null,
	val icon: ImageVector,
) {
	
	abstract val pageFunction: @Composable (VM) -> Unit
	open val fab: FabConfig<VM>? = null
	
	fun compose(it: NavGraphBuilder, vm: VM) {
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
		androidx.compose.material3.Icon(
			imageVector = icon,
			contentDescription = "A Home Button For The App"
		)
	}
	
	@Composable
	fun Fab(vm: VM) {
		fab?.compose(vm)
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