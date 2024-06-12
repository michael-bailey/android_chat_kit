package io.github.michael_bailey.android_chat_kit.activity.debug_activity.servers_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.activity.debug_activity.DebugActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServerDialogue(
	vm: DebugActivityViewModel,
	nav: NavController,
	host: String,
	port: String
) {
	
	val (host, setHost) = remember { mutableStateOf(host) }
	val (port, setPort) = remember { mutableStateOf(port) }
	
	BasicAlertDialog(onDismissRequest = { nav.popBackStack() }) {
		Card() {
			Column(
				modifier = Modifier
					.padding(20.dp)
					.wrapContentSize(),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Text(fontSize = 20.sp, fontWeight = FontWeight.Bold, text = "Add Server")
				OutlinedTextField(value = host, onValueChange = setHost, label = { Text("host") })
				OutlinedTextField(value = port, onValueChange = setPort, label = { Text("Port") })
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.End)
				) {
					Button(onClick = { nav.popBackStack() }) {
						Text(text = "Cancel")
					}
					Button(onClick = {
						vm.add_server(host, port)
						nav.popBackStack()
					}) {
						Text(text = "Add")
					}
				}
			}
		}
	}
}
