package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.michael_bailey.android_chat_kit.data_class.ProfileAuthenticationToken
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import io.github.michael_bailey.android_chat_kit.utils.EncryptionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class ProfileLoginViewModel(
	private val profileDao: EntProfileDao,
): BaseProfileLoginViewModel(profileDao.queryProfileOverviews()) {

	override fun createProfile(username: String, password: String) {
		viewModelScope.launch(Dispatchers.IO) {
			profileDao.insertProfile(
				EntProfile(username = username, password = password)
			)
		}
	}

	override fun getLoginToken(uuid: UUID, password: String, onSuccess: () -> Unit): Result<Unit> = runCatching {
		val profile = profileDao.loadProfile(uuid, password)
		val token = ProfileAuthenticationToken(uuid, EncryptionUtils.)
	}

	class Factory(
		private val profileDao: EntProfileDao
	): ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return ProfileLoginViewModel(profileDao) as T
		}
	}
}