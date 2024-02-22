package io.github.michael_bailey.android_chat_kit.activity.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UserNamePage(
	vm: OnboardingActivityViewModel,
	nav: NavController,
	padding: PaddingValues
) {
	
	val activity = LocalContext.current as OnboardingActivity
	
	val uuid by vm.uuid.observeAsState()
	val username by vm.username.observeAsState()
	val isSubmittable by vm.isNameSubmittable.observeAsState(initial = false)
	
	Box(
		modifier = Modifier
			.padding(padding)
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Column(modifier = Modifier.fillMaxWidth(0.75f)) {
			Row(
				Modifier,

				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					modifier = Modifier
						.fillMaxWidth(),
					text = uuid.toString()
				)
			}
			OutlinedTextField(
				modifier = Modifier
					.padding(top = 25.dp)
					.fillMaxWidth(),
				value = username ?: "",
				onValueChange = vm::setUsername,
				placeholder = { Text("Username...")}
			)
			Row(
				Modifier
					.padding(top = 100.dp)
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				Button(onClick = { /*TODO*/ }) {
					Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "")
				}
				Button(
					onClick = {
						activity.goToHomePage()
					},
					enabled = isSubmittable
				) {
					Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = "")
				}
			}
		}
	}
}
