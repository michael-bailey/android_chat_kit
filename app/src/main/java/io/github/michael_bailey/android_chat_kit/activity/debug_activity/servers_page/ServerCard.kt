package io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CallEnd
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PhoneInTalk
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionState
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateConnected
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateConnecting
import io.github.michael_bailey.android_chat_kit.service.state.ServerConnectionStateDisconnected
import java.util.UUID

@Composable
fun ServerCard(
	serverId: UUID,
	serverState: ServerConnectionState,
	viewModel: DebugActivityServersViewModel
) {
	
	
	val server by viewModel.getServerFlow(serverId).collectAsState(null)
	
	val status by remember {
		derivedStateOf {
			when (server!!.status) {
				ServerData.Companion.ServerConnectionStatus.Online -> "Online"
				ServerData.Companion.ServerConnectionStatus.Offline -> "Offline"
				ServerData.Companion.ServerConnectionStatus.Unknown -> "Unknown"
			}
		}
	}
	
	if (server == null) {
		Log.i("ServerCard", "server is null")
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight()
		) {
			Text(text = "This UUID does not have a server")
		}
		return
	}
	
	Log.i("ServerCard", "server is not null")
	
	Card(
	 modifier = Modifier
		 .fillMaxWidth()
		 .wrapContentHeight()
	) {
		Row(
			modifier = Modifier
				.padding(20.dp)
				.fillMaxWidth()
				.height(IntrinsicSize.Min),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Column(
				modifier = Modifier.fillMaxHeight(),
				verticalArrangement = Arrangement.SpaceEvenly,
			) {
				Text(
					modifier = Modifier.fillMaxWidth(0.75f),
					fontSize = 20.sp,
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					text = server!!.hostname,overflow = TextOverflow.Ellipsis
				)
				Text(
					modifier = Modifier.fillMaxWidth(0.75f),
					fontSize = 14.sp,
					maxLines = 1,
					text = status
				)
				Text(text = server!!.name)
				Text(text = server!!.owner)
			}
			ActionButtons(server = server!!, serverState = serverState, viewModel = viewModel)
		}
	}
}


@Composable
fun ActionButtons(
	server: ServerData,
	serverState: ServerConnectionState,
	viewModel: DebugActivityServersViewModel
) {
	
	var canDial: Boolean = false
	var canDisconnect: Boolean = false
	var canReload: Boolean = false
	var canDelete: Boolean = false
	
	when (serverState) {
		is ServerConnectionStateDisconnected -> {
			canDial = true
			canReload = true
			canDelete = true
		}
		is ServerConnectionStateConnecting -> {
			canReload = true
		}
		is ServerConnectionStateConnected -> {
			canDial = false
			canDisconnect = serverState.serverData.uuid == server.uuid
			canReload = true
			canDelete = true
		}
	}
	
	val onDial: (UUID) -> Unit = {  }
	val onDisconnect: (UUID) -> Unit = {  }
	val onReload: (UUID) -> Unit = {  }
	val onDelete: (UUID) -> Unit = {  }
	
	
	Column {
		if (!canDisconnect) {
			IconButton(
				enabled = canDial,
				onClick = { viewModel.dial(server.uuid!!) },
				colors = IconButtonDefaults.filledIconButtonColors(
					containerColor = Color.Green
				)
			) {
				Icon(imageVector = Icons.Outlined.PhoneInTalk, contentDescription = "")
			}
		} else {
			IconButton(
				enabled = canDisconnect,
				onClick = { viewModel.disconnect() },
				colors = IconButtonDefaults.filledIconButtonColors(
					containerColor = Color.Red
				)
			) {
				Icon(imageVector = Icons.Outlined.CallEnd, contentDescription = "")
			}
		}
		IconButton(
			enabled = canReload,
			onClick = { viewModel.reload(server.uuid!!) },
			colors = IconButtonDefaults.filledIconButtonColors(
				containerColor = Color.Yellow
			)
		) {
			Icon(imageVector = Icons.Outlined.CloudDownload, contentDescription = "")
		}
		IconButton(
			enabled = canDelete,
			onClick = { viewModel.delete(server.uuid!!) },
			colors = IconButtonDefaults.filledIconButtonColors(
				containerColor = Color.Red
			)
		) {
			Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
		}
	}
}