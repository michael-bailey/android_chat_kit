package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
	val fetchToken: () -> Unit,
	val profileDao: EntProfileDao,
	private val _loggedInUser: MutableLiveData<EntProfileDao.EntProfileOverview?> =
		MutableLiveData<EntProfileDao.EntProfileOverview?>()
): AbstractHomeViewModel(
	fetchToken
) {

	val loggedInUser: LiveData<EntProfileDao.EntProfileOverview?> = _loggedInUser

	override fun onTokenSet() {
		token.value?.let {
			viewModelScope.launch(Dispatchers.IO) {
				val profile = profileDao.queryProfileOverview(it.first)
				viewModelScope.launch(Dispatchers.Main) {
					_loggedInUser.value = profile
				}
			}
		}
	}

	init {
		if (loggedInUser.value == null) {
			fetchToken()
		}
	}

	class Factory(
		val fetchToken: () -> Unit,
		val profileDao: EntProfileDao,
	): ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return HomeViewModel(fetchToken, profileDao) as T
		}
	}
}
