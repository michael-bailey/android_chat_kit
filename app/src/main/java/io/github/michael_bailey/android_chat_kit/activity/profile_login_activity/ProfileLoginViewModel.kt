package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import java.util.UUID

class ProfileLoginViewModel(
	private val profileDao: EntProfileDao,
	returnResult: (UUID, String) -> Unit
): AbstractProfileLoginViewModel(profileDao.queryProfileOverviews(), returnResult) {

	override suspend fun createProfile(username: String, password: String) {
			val profile = EntProfile.create(username = username, password = password)
			profileDao.insertProfile(
				profile
			)
			returnResult(profile.uuid, profile.password)
	}

	override suspend fun loginProfile(
		uuid: UUID,
		password: String,
	) {
		profileDao.loadProfile(uuid, password)
			.onSuccess {
				returnResult(it.uuid, it.password)
			}.getOrThrow()
//			.onFailure {
//				viewModelScope.launch(Dispatchers.Main) { this@ProfileLoginViewModel._passwordError.value = it.message }
//			}
	}

	class Factory(
		val profileDao: EntProfileDao,
		private val returnResult: (UUID, String) -> Unit
	): ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return ProfileLoginViewModel(profileDao, returnResult) as T
		}
	}
}