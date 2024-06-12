package io.github.michael_bailey.android_chat_kit.activity.debug_activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsPanelButton(
	nav: NavController,
	route: String,
	name: String,
	description: String,
	icon: ImageVector,
) {
	Surface(
		modifier = Modifier.clickable(
			interactionSource = remember { MutableInteractionSource() },
			indication = rememberRipple(bounded = true), onClick = { nav.navigate(route = route) }
		),
		shape = RoundedCornerShape(20.dp)
	) {
		Row(
			modifier = Modifier
				.padding(8.dp)
				.wrapContentHeight()
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.Start,
			Alignment.CenterVertically
		) {
			Icon(imageVector = icon, contentDescription = "")
			Column(modifier = Modifier.padding(horizontal = 16.dp)) {
				Text(fontSize = 24.sp, text = name)
				Text(fontSize = 16.sp, text = description)
			}
		}
	}

}
