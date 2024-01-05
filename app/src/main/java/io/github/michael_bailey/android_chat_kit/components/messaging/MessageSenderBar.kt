package io.github.michael_bailey.android_chat_kit.components.messaging

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.android_chat_kit.interfaces.IMessageSendBarController

@Composable
fun RowScope.MessageSenderBar(vm: IMessageSendBarController) {
	
	var textState by remember { mutableStateOf("") }
	
	OutlinedTextField(
		modifier = Modifier.width(325.dp).padding(horizontal = 8.dp),
		value = textState,
		onValueChange = { textState = it }
	)
	FloatingActionButton(
		onClick = {
			if (textState.isNotEmpty()) {
				vm.sendGlobalMessage(textState)
				textState = ""
			}
		}
	) {
		Icon(Icons.Outlined.Send, contentDescription = "")
	}
}