package io.github.michael_bailey.android_chat_kit.activity.home_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.broadcasters.LoginStatusBroadcastReceiver
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import io.github.michael_bailey.android_chat_kit.repository.ProfileRepository
import io.github.michael_bailey.android_chat_kit.repository.TokenRepository
import io.github.michael_bailey.android_chat_kit.utils.login.LoginStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val loginRepository: LoginRepository,
	private val profileRepository: ProfileRepository,
	private val tokenRepository: TokenRepository,
	private val localBroadcastManager: LocalBroadcastManager,
	
): ViewModel() {
	
	private val _isLoggedIn = MutableStateFlow(loginRepository.isLoggedIn)
	private val _profile = _isLoggedIn.map {
		tokenRepository.getToken()?.first?.let { it1 ->
			profileRepository.getProfileOverview(
				it1
			)
		}
	}
	//	private val _server =

	val profileOverview = _profile.asLiveData()
	val isLoggedIn = _isLoggedIn.asLiveData()
	
	fun setProfileToken(token: Pair<UUID, String>?) = viewModelScope.launch(Dispatchers.Main) {
		if (token == null) return@launch
		loginRepository.setLoginToken(token)
		_isLoggedIn.emit(loginRepository.isLoggedIn)
	}
	
	fun logout(ctx: Context) = viewModelScope.launch {
		loginRepository.logout()
		_isLoggedIn.emit(loginRepository.isLoggedIn)
		LoginStatusBroadcastReceiver.broadcast(ctx, LoginStatus.LoggedOut)
	}
}
