package io.github.michael_bailey.android_chat_kit.components.messaging

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.MessageSenderBar(onSend: (String) -> Boolean) {
	
	var textState by remember { mutableStateOf("") }
	Card(
		modifier = Modifier
			.padding(1.dp)
			.weight(1f),
		shape = RoundedCornerShape(50),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant,
		)
	) {
		BasicTextField(
			modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth(),
			value = textState,
			textStyle = TextStyle.Default.copy(
				color = MaterialTheme.colorScheme.onSurface
			),
			onValueChange = { textState = it },
			cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
			
		)
	}

	IconButton(
		modifier = Modifier
			.padding(1.dp),
		colors = IconButtonDefaults.iconButtonColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant,
		),
		onClick = {
			if (textState.isNotEmpty()) {
				onSend(textState)
				textState = ""
			}
		}
	) {
		Icon(imageVector = Icons.Outlined.Send, contentDescription = "")
	}
}