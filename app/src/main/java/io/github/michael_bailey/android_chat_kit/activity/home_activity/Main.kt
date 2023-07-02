package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.android_chat_kit.activity.home_activity.Page.Companion.Chat
import io.github.michael_bailey.android_chat_kit.activity.home_activity.Page.Companion.Home
import io.github.michael_bailey.android_chat_kit.activity.home_activity.Page.Companion.Server
import io.github.michael_bailey.android_chat_kit.activity.home_activity.Page.Companion.Chat.NavItem as ChatNavItem
import io.github.michael_bailey.android_chat_kit.activity.home_activity.Page.Companion.Home.NavItem as HomeNavItem
import io.github.michael_bailey.android_chat_kit.activity.home_activity.Page.Companion.Server.NavItem as ServerNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: HomeViewModel) {
	
	val context = LocalContext.current
	val nav = rememberNavController()
	val profile by vm.profileOverview.observeAsState()
	
	val colour = Color(SurfaceColors.SURFACE_2.getColor(context))
	
	Scaffold(
		containerColor = colour,
		topBar = {
			TopAppBar(
				title = { Text(text = "Header") },
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = Color(SurfaceColors.SURFACE_2.getColor(context)
					)
				),
				actions = {
					IconButton(onClick = { vm.logout(context) }) {
						Icon(
							imageVector = Icons.Outlined.ExitToApp,
							contentDescription = "Logout button"
						)
					}
				}
			)
		},
		content = {
			Surface(
				modifier = Modifier
					.padding(it)
					.fillMaxSize(),
				shape =  RoundedCornerShape(20.dp),
				color = MaterialTheme.colorScheme.surface
			) {
				Column(modifier = Modifier.padding(10.dp, vertical = 0.dp)) {
					NavHost(navController = nav, "home") {
						Home.compose(this, vm)
						Chat.compose(this, vm)
						Server.compose(this, vm)
					}
				}
			}
		},
		bottomBar = {
			NavigationBar {
				HomeNavItem(nav)
				ChatNavItem(nav)
				ServerNavItem(nav)
			}
		}
	)
}