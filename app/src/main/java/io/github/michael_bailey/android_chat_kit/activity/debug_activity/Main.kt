package io.github.michael_bailey.android_chat_kit.activity.debug_activity

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page.AddServerDialogue
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page.DebugActivityServersViewModel
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page.ServerSettingsPage
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.user_page.UserDetailsPage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Main(
	viewModel: DebugActivityViewModel,
	serverViewModel: DebugActivityServersViewModel
) {
	
	val nav = rememberNavController()
	
	val topAppBarState = rememberTopAppBarState()
	val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
	val scrollState = rememberLazyListState()
	
//	val servers by vm.servers.observeAsState(initial = listOf())
	
	NavHost(
		modifier = Modifier
			.fillMaxSize(),
		navController = nav,
		startDestination = "Main",
	) {
		
		composable(
			route = "Main",
			deepLinks = listOf(
				navDeepLink { uriPattern = "${DebugActivity.URI}/" },
			)
		) {
			DebugMenuPage(
				nav = nav,
				scrollBehavior = scrollBehavior,
				scrollState = scrollState
			)
		}
		
		// servers page
		navigation(
			route = "Servers",
			startDestination = "Server_List"
		) {
			
			composable(
				route = "Server_List",
				deepLinks = listOf(
					navDeepLink { uriPattern = "${DebugActivity.URI}/servers" }
				)
			) {
				ServerSettingsPage(nav = nav, viewModel = serverViewModel)
			}
			
			dialog(
				route = "Add_Server?host={host}&port={port}",
				arguments = listOf(
					navArgument("host") { defaultValue = "localhost" },
					navArgument("port") { defaultValue = "5600" }
				),
				deepLinks = listOf(
					navDeepLink { uriPattern = "${DebugActivity.URI}/add_server?host={host}&port={port}" }
				)
			) { entry ->
				AddServerDialogue(
					vm = viewModel,
					nav = nav,
					host = entry.arguments?.getString("host")!!,
					port = entry.arguments?.getString("port")!!,
				)
			}
		}
		
		navigation(
			route = "User",
			startDestination = "User_Details",
		) {
			composable(
				route = "User_Details",
				deepLinks = listOf(
					navDeepLink { uriPattern = "${DebugActivity.URI}/user" }
				)
			) {
				UserDetailsPage(nav = nav)
			}
		}
	}
}