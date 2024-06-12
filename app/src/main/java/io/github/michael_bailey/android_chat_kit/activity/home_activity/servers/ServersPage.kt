package io.github.michael_bailey.android_chat_kit.activity.home_activity.servers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.activity.home_activity.HomeActivity
import io.github.michael_bailey.android_chat_kit.extension.activity.startServerConnectionActivity
import io.github.michael_bailey.android_chat_kit.extension.activity.startServerShareActivity
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IServersViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServersPage(
	vm: IServersViewModel,
	nav: NavController,
	padding: PaddingValues
) {
	
	val context = LocalContext.current as HomeActivity
	
	val servers by vm.savedServers.observeAsState(listOf())
	
	Column(
		modifier = Modifier
			.padding(padding)
			.padding(horizontal = 12.dp),
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
		) {
			this.items(items = servers) {
				Card(shape = RoundedCornerShape(20.dp)) {
					Column(modifier = Modifier
						.padding(20.dp)
						.fillMaxWidth()) {
						Text(it.name)
						Text(it.owner)
						Text("${it.hostname}: ${it.port}", color = Color.Gray)
						Row(modifier = Modifier
							.fillMaxWidth()
							.padding(top = 16.dp), horizontalArrangement =Arrangement.End) {
							Row(
							) {
							 Button(onClick = { context.startServerShareActivity(it) }) {
								 Icon(Icons.Outlined.Share, contentDescription = "")
							 }
							}
							Row(
								horizontalArrangement =Arrangement.End
							) {
								Button(onClick = { log("Unimplemented") }) {
									Icon(Icons.Outlined.Refresh, contentDescription = "")
								}
								Button(
									modifier = Modifier.padding(horizontal = 12.dp),
									onClick = { context.startServerConnectionActivity(it.hostname, it.port) }
								) {
									Icon(Icons.Outlined.ArrowForward, contentDescription = "")
								}
							}
						}
					}
				}
			}
			
			item {
				Button(onClick = { context.openDebugger() }) {
					Text(text = "Open Debugger")
				}
			}
		}
	}
}
