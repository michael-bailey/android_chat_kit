package io.github.michael_bailey.android_chat_kit.components.messaging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.map
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IChatListViewModel

@Composable
fun ChatListView(vm: IChatListViewModel) {
	
	val groupedMessages by vm.groupedMessage.map { it.reversed() }.observeAsState(listOf())
	
	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		reverseLayout = true,
		content = {
			items(groupedMessages!!) {
				Column(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
					
					if (it.isNotEmpty()) Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = if (it.first().isReceived) { Arrangement.Start } else { Arrangement.End },
					) {
						Text(modifier = Modifier.padding(horizontal = 12.dp) ,text = it.first().sender)
					}
					
					for (i in it) {
						ChatMessageView(i, it.indexOf(i) == 0, it.indexOf(i) == it.lastIndex)
					}
				}
			}
		}
	)
}