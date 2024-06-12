package io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateDisconnected

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServerList(
	padding: PaddingValues,
	nav: NavController,
	scrollState: LazyListState,
	viewModel: DebugActivityServersViewModel,
) {
	
	val status by viewModel.connectionStatus.observeAsState(initial = "Invalid")
	val state by viewModel.connectionState.observeAsState(initial = ServerConnectionStateDisconnected)
	
	val serverList by viewModel.servers.observeAsState(listOf())
	
	val connectedServer by viewModel.connectedServer.observeAsState()
	
	val (tmpState, setTmpState) = remember { mutableStateOf(false) }
	
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
			.padding(padding),
		contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
		state = scrollState
	) {
		
		item {
			Column(
				Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Card(
					modifier = Modifier.fillMaxWidth(),
					shape = RoundedCornerShape(25.dp)
				) {
					Row(
						modifier = Modifier.padding(20.dp).fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Absolute.SpaceBetween
					) {
						Text(
							text = "Continuous Pinging",
							fontWeight = FontWeight.Bold,
							fontSize = 20.sp,
						)
						Switch(checked = tmpState, onCheckedChange = setTmpState)
					}
				}
				Text(text = status)
				StickyHeaderServer( vm = viewModel, nav = nav)
			}
		}

		if (serverList.isEmpty()) {
			item {
				Text(text = "No servers added")
			}
		} else {
			items(items = serverList, key = {it.uuid!!}) {
				ServerCard(
					serverId = it.uuid!!,
					serverState = state,
					viewModel = viewModel
				)
			}
		}
	}
}