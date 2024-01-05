package io.github.michael_bailey.android_chat_kit.components.navigation

import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.android_chat_kit.components.scopes.PageScaffoldScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageScaffold(
	startRoute: String,
	routeBuilder: PageScaffoldScope<PageScaffoldScope.BasicPageData>.(NavController) -> Unit
) {
	val nav = rememberNavController()
	
	val currentRoute by nav
		.currentBackStackEntryFlow
		.collectAsState(initial = nav.currentBackStackEntry)
	
	val currentRouteName by remember {
		derivedStateOf {
			currentRoute?.id ?: startRoute
		}
	}
	
	val scope = PageScaffoldScope<PageScaffoldScope.BasicPageData>()
	
	scope.routeBuilder(nav)
	
	val pages = scope.getPage().associate { it.route to it }
	
	Scaffold(
		topBar = {
			if (pages[currentRouteName]?.title != null) {
				TopAppBar {
					Text(text = pages[currentRouteName]?.title ?: "")
				}
			}
		},
		content = { padding ->
			NavHost(nav, startRoute) {
				for (i in pages) {
					composable(i.key) {
						i.value.composable(nav, padding)
					}
				}
			}
		},
	)
}