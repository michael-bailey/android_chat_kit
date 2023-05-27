package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileLoginViewModel @Inject constructor(
//	private val loginRepository: LoginRepository,
	private val profileRepository: TokenRepository,
	profileDao: EntProfileDao,
	): ViewModel() {
	
	private val _passwordFieldFlow: MutableStateFlow<String> = MutableStateFlow("")
	private val _passwordIncorrectFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
	private val _activeProfileCardFlow: MutableSharedFlow<Int?> = MutableSharedFlow()
	private val _recentToken: MutableSharedFlow<Pair<UUID, String>?> = MutableSharedFlow()
	
	val profiles: LiveData<List<EntProfileDao.EntProfileOverview>> = profileDao.queryProfileOverviews()
	val activeProfileCard: Flow<Int?> = _activeProfileCardFlow
	val isIncorrect: Flow<Boolean> = _passwordIncorrectFlow
	val recentToken: Flow<Pair<UUID, String>?> = _recentToken
	
	val passwordError: Flow<String?> = flow {
		_passwordFieldFlow.collect { emit(profileRepository.getPasswordErrors(it)?.message) }
	}
	
	val isPasswordValid = flow<Boolean> {
		_passwordFieldFlow.collect { emit(profileRepository.getPasswordErrors(it)?.message == null) }
	}
	
	fun activateProfileCard(index: Int?) {
		viewModelScope.launch {
			_passwordFieldFlow.emit("")
			_passwordIncorrectFlow.emit(false)
			_activeProfileCardFlow.emit(index)
		}
	}
	
	fun updatePassword(password: String) {
		viewModelScope.launch(Dispatchers.IO) {
			_passwordFieldFlow.emit(password)
		}
	}
	
	fun create(
		username: String,
		password: String,
	) {
		viewModelScope.launch(Dispatchers.IO) {
			val token = profileRepository.createAndGetToken(username, password).getOrNull()
			_recentToken.emit(token)
			
		}
	}
	
	/**
	 * requests logging in to a profile.
	 */
	fun login(
		uuid: UUID,
		password: String,
	) {
		viewModelScope.launch(Dispatchers.IO) {
			val token = profileRepository.getToken(uuid, password).getOrNull()
			_recentToken.emit(token)
		}
	}
}