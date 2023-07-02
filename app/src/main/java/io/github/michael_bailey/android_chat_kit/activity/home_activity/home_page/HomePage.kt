package io.github.michael_bailey.android_chat_kit.activity.home_activity.home_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.michael_bailey.android_chat_kit.activity.home_activity.HomeViewModel

@Composable
fun HomePage(vm: HomeViewModel) {
	Column(
		Modifier.fillMaxHeight().fillMaxWidth(0.9f),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text("Home Page")
	}
}

//Surface(
//modifier = Modifier
//.fillMaxSize()
//.height(75.dp)
//.shadow(10.dp, shape = RoundedCornerShape(20.dp)),
//shape = RoundedCornerShape(10.dp),
//color = MaterialTheme.colorScheme.surface,
//elevation = -10.dp
//) {}