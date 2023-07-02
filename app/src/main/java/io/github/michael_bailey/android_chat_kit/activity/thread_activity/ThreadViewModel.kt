package io.github.michael_bailey.android_chat_kit.activity.thread_activity

import androidx.lifecycle.ViewModel
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import io.github.michael_bailey.android_chat_kit.repository.ProfileRepository
import io.github.michael_bailey.android_chat_kit.repository.TokenRepository
import javax.inject.Inject

class ThreadViewModel @Inject constructor(
	val profileRepository: ProfileRepository,
	val loginRepository: LoginRepository,
	val tokenRepository: TokenRepository,
): ViewModel() {
	init {
		profileRepository
		
	}
}