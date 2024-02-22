package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.android_chat_kit.activity.home_activity.profile.ProfilePage
import io.github.michael_bailey.android_chat_kit.activity.home_activity.servers.ServersPage
import io.github.michael_bailey.android_chat_kit.components.configs.FABData
import io.github.michael_bailey.android_chat_kit.components.dialog.AddServerDialog
import io.github.michael_bailey.android_chat_kit.components.navigation.BottomNavigationPageScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: HomeActivityViewModel) {
	
	val context = LocalContext.current as HomeActivity
	val nav = rememberNavController()
	val colour = Color(SurfaceColors.SURFACE_2.getColor(context))
	
	val currentRoute = nav
		.currentBackStackEntryFlow
		.collectAsState(initial = nav.currentBackStackEntry)
	
	var isAddServerShown by remember { mutableStateOf(false) }
	
	if (isAddServerShown) AddServerDialog(
		dismiss = { isAddServerShown = false },
		onSubmit = {
//			context.startServerConnectionActivity(it, 5600)
			vm.addServer(it)
			true
		}
	)
	
	BottomNavigationPageScaffold(startRoute = "server") {
		
		/* Home page */
		bottomPage(
			route = "server",
			title = "Servers List",
			icon = Icons.Outlined.Person,
			label = "Servers",
			fab = FABData(
				icon = Icons.Outlined.Add
			) { isAddServerShown = true }
		) { nav, pad ->
			ServersPage(vm = vm, nav = nav, padding = pad!!)
		}
		
		/* View profile page */
		bottomPage(
			route = "profile",
			title = "Profile",
			icon = Icons.Outlined.Person,
			label = "Profile"
		) { nav, pad ->
			Box(modifier = Modifier.padding(pad!!), contentAlignment = Alignment.Center) {
				ProfilePage(vm = vm, nav = nav, padding = pad)
			}
		}
	}
}