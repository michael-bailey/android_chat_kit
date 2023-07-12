package io.github.michael_bailey.android_chat_kit.activity.connected_server_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: ConnectedServerViewModel) {
	
	val scrollState = TopAppBarDefaults.pinnedScrollBehavior()
	
	Scaffold(
		modifier = Modifier.nestedScroll(scrollState.nestedScrollConnection),
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Server Connection") },
				scrollBehavior = scrollState
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
				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					verticalArrangement = Arrangement.Top,
					horizontalAlignment = Alignment.CenterHorizontally,
					content = {
						item {
							Text(text = "Content")
						}
					}
				)
			}
		},
		bottomBar = {}
	)
}