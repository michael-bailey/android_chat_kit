package io.github.michael_bailey.android_chat_kit.activity.debug_activity.user_page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page.DebugActivityServersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsPage(nav: NavController) {
	val viewModel = hiltViewModel<DebugActivityServersViewModel>()
	
	val topAppBarState = rememberTopAppBarState()
	val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
	val scrollState = rememberLazyListState()
	
	Scaffold(
		modifier = Modifier
			.fillMaxSize()
			.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			LargeTopAppBar(
				title = {
					Text(text = "User Details", maxLines = 1, overflow = TextOverflow.Ellipsis)
				},
				navigationIcon = {
					IconButton(
						modifier = Modifier.padding(start = 8.dp, end = 20.dp),
						onClick = { nav.popBackStack(route = "Main", inclusive = false, saveState = true) }
					) {
						Icon( imageVector = Icons.Outlined.ArrowBack, contentDescription = "" )
					}
				},
				scrollBehavior = scrollBehavior
			)
		},
		content = {
			UserDetails(
				padding = it,
				nav = nav,
				scrollState = scrollState,
				vm = viewModel
			)
		}
	)
}

