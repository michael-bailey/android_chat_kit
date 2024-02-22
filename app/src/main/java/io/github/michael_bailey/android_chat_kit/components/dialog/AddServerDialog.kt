package io.github.michael_bailey.android_chat_kit.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.michael_bailey.android_chat_kit.theme.dialogHeader

@Composable
fun AddServerDialog(
	dismiss: () -> Unit,
	onSubmit: (String) -> Boolean
) {
	
	var hostname by remember { mutableStateOf("") }
	
	Dialog(onDismissRequest = dismiss) {
		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			shape = RoundedCornerShape(28.dp),
			content = {
				Column(modifier = Modifier.padding(20.dp)) {
					Text(modifier = Modifier.padding(bottom = 12.dp), text = "Add Server", fontSize = dialogHeader)
					OutlinedTextField(value = hostname, onValueChange = { hostname = it })
					Row(
						modifier = Modifier.fillMaxWidth(0.9f),
						Arrangement.SpaceEvenly,
						Alignment.CenterVertically
					) {
						Button(onClick = dismiss) {
							Text(text = "Cancel")
						}
						Button(onClick = {
							if (onSubmit(hostname)) {
								dismiss()
							}
						}) {
							Text(text = "Submit")
						}
					}
				}
			}
		)
	}
}
