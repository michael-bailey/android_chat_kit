package io.github.michael_bailey.android_chat_kit.activity.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import io.github.michael_bailey.android_chat_kit.components.navigation.PageScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: OnboardingActivityViewModel) {
	
	PageScaffold(startRoute = "welcome") {
		page("username", composable = { nav, pad -> UserNamePage(vm, it, pad!!) })
		page("welcome", composable = { nav, pad -> WelcomePage(vm, it, pad!!) })
	}
}