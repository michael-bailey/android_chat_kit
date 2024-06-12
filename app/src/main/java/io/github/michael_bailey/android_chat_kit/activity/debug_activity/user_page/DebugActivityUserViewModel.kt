package io.github.michael_bailey.android_chat_kit.activity.debug_activity.user_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DebugActivityUserViewModel @Inject constructor(
	private val loginRepository: LoginRepository
): ViewModel() {
	val userId = loginRepository.uuid.map { it.toString() }.asLiveData()
	val username = loginRepository.username.asLiveData()
	
	fun setUserId(userId: String) {
	
	}
	
	fun setUsername(username: String) {
	
	}

	
}