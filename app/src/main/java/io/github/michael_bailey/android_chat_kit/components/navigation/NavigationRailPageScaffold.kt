package io.github.michael_bailey.android_chat_kit.components.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.android_chat_kit.components.configs.FABData
import io.github.michael_bailey.android_chat_kit.components.scopes.NavigationRailPageScaffoldScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRailPageScaffold(
	startRoute: String,
	fab: FABData? = null,
	routeBuilder: NavigationRailPageScaffoldScope.(NavController) -> Unit
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
	
	val scope = NavigationRailPageScaffoldScope()
	
	scope.routeBuilder(nav)
	
	val pages = scope.getPage().associate { it.route to it }
	
	
	Scaffold(
		topBar = {
			TopAppBar(title = { Text(text = "Temp") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(SurfaceColors.SURFACE_2.getColor(context))))
		},
		content = { pad ->
			
			Row(
				modifier = Modifier.fillMaxSize().padding(pad)
			) {
				NavigationRail(
					containerColor = Color(SurfaceColors.SURFACE_2.getColor(context)),
					contentColor = MaterialTheme.colorScheme.surface,
					header = {
						FloatingActionButton(
							onClick = {  }
						) {
							Icon(imageVector = Icons.Outlined.Message, contentDescription = "")
						}
					},
					content = {
						
						for (i in pages) {
							if (i.value::class == NavigationRailPageScaffoldScope.NavigationPageData::class) {
								val data = (i.value as NavigationRailPageScaffoldScope.NavigationPageData)
								
								Log.d("NavigationBar", "BottomNavigationPageScaffold: i.key is ${i.key}")
								Log.d("NavigationBar", "BottomNavigationPageScaffold: currentRouteName is $currentRouteName")
								
								NavigationRailItem(
									selected = currentRouteName == i.key,
									onClick = {nav.navigate(i.key)},
									icon = {
										Icon(
											imageVector = data.icon,
											contentDescription = ""
										)
									},
									label = { Text(text = data.label) }
								)
							}
						}
					}
				)
				NavHost(
					modifier = Modifier.fillMaxSize(),
					navController = nav,
					startDestination = startRoute
				) {
					for (i in pages) {
						composable(i.key) {
							i.value.composable(nav, PaddingValues(0.dp))
						}
					}
				}
			}
		}
	)
}
