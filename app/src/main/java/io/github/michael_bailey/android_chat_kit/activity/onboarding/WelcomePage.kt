package io.github.michael_bailey.android_chat_kit.activity.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.michael_bailey.android_chat_kit.theme.header

@Composable
fun WelcomePage(
	vm: OnboardingActivityViewModel,
	nav: NavController,
	padding: PaddingValues
) {
	Box(
		modifier = Modifier
			.padding(padding)
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(
				modifier = Modifier.padding(bottom = 100.dp),
				textAlign = TextAlign.Center,
				text = "Welcome",
				fontSize = header
			)
			Text(
				modifier = Modifier.padding(vertical = 5.dp),
				text = "To chat kit",
				textAlign = TextAlign.Justify,
			)
			Text(
				text = "A distributed messaging system!",
				textAlign = TextAlign.Justify,
			)
			Text(
				modifier = Modifier.padding(top = 20.dp),
				text = "We're going to get you started",
				textAlign = TextAlign.Justify,
			)
			Text(
				text = "With a couple of questions",
				textAlign = TextAlign.Justify,
			)
			Button(
				modifier = Modifier.padding(top = 100.dp),
				onClick = { nav.navigate("username") }
			) {
				Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = "")
			}
		}
	}
}