package io.github.michael_bailey.android_chat_kit.components.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.android_chat_kit.components.scopes.BottomNavigationPageScaffoldScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationPageScaffold(
	startRoute: String,
	routeBuilder: BottomNavigationPageScaffoldScope.(NavController) -> Unit
) {
	val context = LocalContext.current as Activity
	val nav = rememberNavController()
	
	val currentRoute by nav
		.currentBackStackEntryFlow
		.collectAsState(initial = nav.currentBackStackEntry)
	
	val currentRouteName by remember {
		derivedStateOf {
			currentRoute?.destination?.route ?: startRoute
		}
	}
	
	val scope = BottomNavigationPageScaffoldScope()
	
	scope.routeBuilder(nav)
	
	val pages = scope.getPage().associate { it.route to it }
	
	
	Scaffold(
		topBar = {
			if (pages[currentRouteName]?.title != null) {
				TopAppBar(title = { Text(text = pages[currentRouteName]?.title ?: "") })
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
		floatingActionButton = {
			val page = pages[currentRouteName]
			
			
			if (
				page != null &&
				page::class == BottomNavigationPageScaffoldScope.NavigationPageData::class &&
				(page as BottomNavigationPageScaffoldScope.NavigationPageData).fab != null
			) {
				
				val data = (pages[currentRouteName] as BottomNavigationPageScaffoldScope.NavigationPageData)
				
				FloatingActionButton(
					onClick = data.fab?.onClick ?: {},
				) {
					Icon(imageVector = data.fab?.icon ?: Icons.Outlined.Error, contentDescription = "")
					if (data.fab?.label != null) {
						Text(text = data.fab.label)
					}
 				}
			}
		},
		bottomBar = {
			NavigationBar {
				for (i in pages) {
					if (i.value::class == BottomNavigationPageScaffoldScope.NavigationPageData::class) {
						val data = (i.value as BottomNavigationPageScaffoldScope.NavigationPageData)
						
						Log.d("NavigationBar", "BottomNavigationPageScaffold: i.key is ${i.key}")
						Log.d("NavigationBar", "BottomNavigationPageScaffold: currentRouteName is $currentRouteName")
						
						NavigationBarItem(
							selected = currentRouteName == i.key,
							onClick = {nav.navigate(i.key)},
							icon = {
								Icon(
									imageVector = data.icon,
									contentDescription = ""
								)
							},
							label = {Text(text = data.label)}
						)
					}
				}
			}
		}
	)
}