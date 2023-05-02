package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.android_chat_kit.data_class.ProfileAuthenticationToken
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import java.util.UUID

abstract class BaseProfileLoginViewModel(
	val profiles: LiveData<List<EntProfileDao.EntProfileOverview>>
): ViewModel() {

	abstract fun createProfile(username: String, password: String)
	abstract fun getLoginToken(uuid: UUID, password: String, onSuccess: () -> Unit): Result<Unit>

	class Factory(
		private val profileDao: EntProfileDao
	): ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return ProfileLoginViewModel(profileDao) as T
		}
	}

	object PreviewVM: BaseProfileLoginViewModel(MutableLiveData(listOf())) {
		override fun createProfile(username: String, password: String) {
			(profiles as MutableLiveData).value = profiles.value?.plus(
				EntProfileDao.EntProfileOverview(UUID.randomUUID(), username)
			)
		}

		override fun getLoginToken(
			uuid: UUID,
			password: String
		): Result<ProfileAuthenticationToken> = runCatching {

			ProfileAuthenticationToken(uuid, password)
		}

	}


}