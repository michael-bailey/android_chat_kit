package io.github.michael_bailey.android_chat_kit.components.messaging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ChatMessageView(
	displayText: String,
	isReceived: Boolean,
	isFirst: Boolean = false,
	isLast: Boolean = false
) {
	
	val context = LocalContext.current
	
	// text formatting
	
	var shape = RoundedCornerShape(24.dp)
	val corSize = CornerSize(8.dp)
	
	val a = listOf(isReceived, isFirst, isLast)
	
	// these statements are reversed cause of layouts
	if (isReceived) {
		if (isFirst && !isLast) {
			shape = shape.copy(
				bottomStart = corSize
			)
		} else if (isLast && !isFirst) {
			shape = shape.copy(
				topStart = corSize
			)
		} else if (!isFirst && !isLast) {
			shape = shape.copy(
				topStart = corSize,
				bottomStart = corSize
			)
		}
	}
	
	if (!isReceived) {
		if (isFirst && !isLast) {
			shape = shape.copy(
				bottomEnd = corSize
			)
		} else if (isLast && !isFirst) {
			shape = shape.copy(
				topEnd = corSize
			)
		} else if (!isFirst && !isLast) {
			shape = shape.copy(
				topEnd = corSize,
				bottomEnd = corSize
			)
		}
	}
	
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = if (isReceived) { Arrangement.Start } else { Arrangement.End },
	) {
		Card(
			modifier = Modifier.padding(1.dp),
			shape = shape,
			colors = if (isReceived) { CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer) } else { CardDefaults.cardColors() },
		) {
			Text(
				modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
				text = displayText
			)
		}
	}
}