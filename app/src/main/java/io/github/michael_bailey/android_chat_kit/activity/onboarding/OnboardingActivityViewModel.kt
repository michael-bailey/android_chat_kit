package io.github.michael_bailey.android_chat_kit.activity.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingActivityViewModel @Inject constructor(
	val loginRepository: LoginRepository
): ViewModel() {
	
	var _uuid = loginRepository.uuid
	var _username = loginRepository.username
	
	val _isNameSubmittable = _uuid.combine(_username) { id, username ->
		id != null && username?.isNotEmpty() == true
		
	}
	
	val username = _username.asLiveData()
	val uuid = _uuid.asLiveData()
	
	val isNameSubmittable = _isNameSubmittable.asLiveData()
	
	val hasProfile: Boolean = loginRepository.hasProfile
	
	fun setUsername(username: String) = viewModelScope.launch(Dispatchers.Main) {
		loginRepository.setUsername(username)
	}
}