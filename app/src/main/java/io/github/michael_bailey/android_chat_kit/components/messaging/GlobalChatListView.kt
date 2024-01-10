package io.github.michael_bailey.android_chat_kit.components.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.android_chat_kit.data_type.GlobalChatMessageData
import io.github.michael_bailey.android_chat_kit.extension.list.runningGroupBy

/**
 * a more generic implementation of the chat list,
 * takes a list of user ids that can be received from
 * takes the list of messages from those users,
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlobalChatListView(
	messages: List<GlobalChatMessageData>
) {
	
	val grouped = messages.runningGroupBy { it.senderId }
	
	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		reverseLayout = true,
		contentPadding = PaddingValues(bottom = 32.dp),
		content = {
			for (group in grouped) {
				items(group) {
					ChatMessageView(
						displayText = it.content,
						isReceived = it.isReceived,
						isFirst = group.indexOf(it) == group.lastIndex,
						isLast = group.indexOf(it) == 0
					)
				}
				item {
					Row(
						modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
						horizontalArrangement = if (group.firstOrNull()?.isReceived == true) {
							Arrangement.Start
						} else {
							Arrangement.End
						}
					) {
						Text(text = group.firstOrNull()?.sender ?: "Unknown")
					}
				}
			}
		}
	)
}