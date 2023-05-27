package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: HomeViewModel) {
	
	val context = LocalContext.current
	val navController = rememberNavController()
	val profile by vm.profileOverview.observeAsState()
	
	Scaffold(
		topBar = { TopAppBar(title = { Text(text = "Header")}) },
		content = {
			Surface(modifier = Modifier.padding(it)) {
				NavHost(navController = navController, "Home") {
					composable("Home") {
						Column() {
							Text(
								text = "Hello ${profile?.username ?: ""}!",
							)
							Button(onClick = { vm.logout(context) }) {
								Text("Logout")
							}
						}
					}
				}
			}
		}
	)
}