package io.github.michael_bailey.android_chat_kit.activity.server_share_activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import io.github.michael_bailey.android_chat_kit.extension.activity.startShareSheet

@Composable
fun Main() {
	val activity = (LocalContext.current as ServerShareActivity)
	val vm = activity.vm
	
	val name by vm.serverName.observeAsState("loading...")
	val host by vm.serverHost.observeAsState("loading...")
	val port by vm.serverPort.observeAsState("loading...")
	val QRCode by vm.jsonQRCode.observeAsState()
	
	val url = "chat://$host:$port"
	
	Box(contentAlignment = Alignment.Center) {
		Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
			QRCode?.let { it() }?.asImageBitmap()?.let { Image(bitmap = it, contentDescription = "") }
			Text(text = name)
			Text(text = "$host:$port")
			Button(onClick = { activity.startShareSheet("chat://$host:$port") }) {
				Icon(imageVector = Icons.Outlined.Share, contentDescription = "")
				Text(text = "Share")
			}
		}
	}

}