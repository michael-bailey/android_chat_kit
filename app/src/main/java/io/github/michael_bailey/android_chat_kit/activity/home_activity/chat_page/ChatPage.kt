package io.github.michael_bailey.android_chat_kit.activity.home_activity.chat_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.android_chat_kit.activity.home_activity.HomeViewModel

@Composable
fun ChatPage(vm: HomeViewModel) {
	LazyColumn(
		contentPadding = PaddingValues(0.dp, 10.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		items(3) {
			Button(
				onClick = {},
				content = {
					Row(
						Modifier
							.fillMaxWidth()
							.height(75.dp),
						horizontalArrangement = Arrangement.Start,
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(
							modifier = Modifier.height(50.dp).width(50.dp),
							imageVector = Icons.Filled.Person,
							contentDescription = "Icon",
							tint = MaterialTheme.colors.secondary
						)
						Text(text = "HELLO WORLD", fontSize = 20.sp)
					}
				}
			)
		}
	}
}