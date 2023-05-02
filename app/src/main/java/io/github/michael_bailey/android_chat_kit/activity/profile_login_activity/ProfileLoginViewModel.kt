package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import io.github.michael_bailey.android_chat_kit.utils.EncryptionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileLoginViewModel(
	private val profileDao: EntProfileDao
): ViewModel() {

	val profiles: LiveData<List<EntProfileDao.EntProfileOverview>> = profileDao.queryProfileOverviews()

	fun createProfile(username: String, password: String) {
		viewModelScope.launch(Dispatchers.IO) {
			profileDao.insertProfile(
				EntProfile(username = username, password = password)
			)
		}
	}

	fun getEncryptedUuid(uuid: UUID): String {
		EncryptionUtils.
	}



	class Factory(
		private val profileDao: EntProfileDao
	): ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return ProfileLoginViewModel(profileDao) as T
		}
	}
}