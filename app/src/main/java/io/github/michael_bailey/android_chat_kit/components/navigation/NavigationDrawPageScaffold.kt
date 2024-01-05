package io.github.michael_bailey.android_chat_kit.components.navigation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.android_chat_kit.components.scopes.NavigationDrawPageScaffoldScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawPageScaffold(
	startRoute: String,
	drawerHeader: (@Composable ColumnScope.() -> Unit)? = null,
	routeBuilder: NavigationDrawPageScaffoldScope.(NavController) -> Unit
) {
	val context = LocalContext.current as Activity
	val nav = rememberNavController()
	val scope = rememberCoroutineScope()
	
	val currentRoute by nav
		.currentBackStackEntryFlow
		.collectAsState(initial = nav.currentBackStackEntry)
	
	val currentRouteName by remember {
		derivedStateOf {
			currentRoute?.destination?.route ?: startRoute
		}
	}
	
	val ROUTE_NAME = currentRouteName
	
	val drawer = rememberDrawerState(initialValue = DrawerValue.Closed)
	
	val navScope = NavigationDrawPageScaffoldScope()
	
	navScope.routeBuilder(nav)
	
	val pages = navScope.getPage().associate { it.route to it }
	val bottomPages = pages.filter { it.value::class == NavigationDrawPageScaffoldScope.NavigationPageData::class }
		.map {it.key to it.value as NavigationDrawPageScaffoldScope.NavigationPageData}.toMap()
		.filter { it.value.bottomBar != null }
	
	val navPages = pages
		.filter { it.value::class == NavigationDrawPageScaffoldScope.NavigationPageData::class }.values
		.map {it as NavigationDrawPageScaffoldScope.NavigationPageData}
	
	ModalNavigationDrawer(
		drawerState = drawer,
		drawerContent = {
			ModalDrawerSheet {
				Card(
					modifier = Modifier
						.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
						.fillMaxWidth(),
					shape = RoundedCornerShape(20.dp)
				) {
					Column(
						modifier = Modifier.fillMaxWidth().padding(8.dp)
					) {
						if (drawerHeader != null) {
							drawerHeader()
						} else {
							Text(text = "Loading...")
						}
					}
				}
				for (i in navPages) {
					NavigationDrawerItem(
						modifier = Modifier.padding(horizontal = 12.dp),
						label = { Text( text = i.label) },
						selected = i.route == currentRouteName,
						onClick = {
							nav.navigate(i.route)
							scope.launch {
								drawer.close()
							}
						}
					)
				}
			}
		},
		content = {
			Scaffold(
				topBar = {
					TopAppBar(
						title = { Text(text = "Title") },
						navigationIcon = {
							IconButton(
								onClick = {
									scope.launch {
										if (drawer.isClosed) {
											drawer.open()
										} else {
											drawer.close()
										}
									}
								}
							) {
								Icon(
									imageVector = Icons.Default.Menu,
									contentDescription = null,
								)
							}
						}
					)
				},
				content = { pad ->
					NavHost(
						modifier = Modifier.padding(pad),
						navController = nav,
						startDestination = startRoute
					) {
						for (i in pages) {
							composable(i.key) {
								i.value.composable(nav, pad)
							}
						}
					}
				},
				bottomBar = {
					bottomPages[ROUTE_NAME]?.bottomBar?.bottomBar?.also {
						BottomAppBar {
							it()
						}
					}
				}
			)
		}
	)
}