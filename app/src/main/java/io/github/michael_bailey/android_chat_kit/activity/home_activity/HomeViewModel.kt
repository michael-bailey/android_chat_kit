package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
	val fetchToken: () -> Unit,
	val profileDao: EntProfileDao,
	private val _loggedInUser: MediatorLiveData<EntProfileDao.EntProfileOverview?> =
		MediatorLiveData<EntProfileDao.EntProfileOverview?>()
): AbstractHomeViewModel(
	fetchToken
) {

	val loggedInUser: LiveData<EntProfileDao.EntProfileOverview?> = _loggedInUser

	override fun onTokenSet() {
		_loggedInUser.value = token.value?.let { profileDao.queryProfileOverview(it.first) }
	}

	init {
		if (loggedInUser.value == null) {
			fetchToken()
		}
		
		_loggedInUser.value = null
		_loggedInUser.addSource(token) {
			it?.first?.let { it1 ->
				viewModelScope.launch(Dispatchers.Main) {
					_loggedInUser.value = profileDao.genQueryProfileOverview(it1).value
				}
			}
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
