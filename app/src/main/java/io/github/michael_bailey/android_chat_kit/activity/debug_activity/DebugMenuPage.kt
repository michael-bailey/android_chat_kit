package io.github.michael_bailey.android_chat_kit.activity.debug_activity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugMenuPage(
	nav: NavController,
	scrollBehavior: TopAppBarScrollBehavior,
	scrollState: LazyListState,
) {
	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).fillMaxSize(),
		topBar = {
			LargeTopAppBar(
				title = { Text(text = "Debug Settings") },
				scrollBehavior = scrollBehavior,
			)
		},
		content = {
			SettingList(
				padding = it,
				nav = nav,
				scrollState = scrollState,
			)
		}
	)
}