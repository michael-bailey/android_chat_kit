package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Base class for The profile ViewModel
 *
 * contains methods for creating authenticated Uris
 * as well as state on profile to display
 */
abstract class AbstractProfileLoginViewModel(
	val profiles: LiveData<List<EntProfileDao.EntProfileOverview>>,

	protected val returnResult: (UUID, String) -> Unit,
	protected val _passwordError: MutableLiveData<String> = MutableLiveData(""),
): ViewModel() {

	val passwordError: LiveData<String> = _passwordError

	/**
	 * called when new profile is requested
	 */
	protected abstract suspend fun createProfile(
		username: String, 
		password: String,
	)

	fun create(
		username: String,
		password: String,
	) {
		viewModelScope.launch {
			createProfile(username, password)
		}
	}


	/**
	 * called on login requested
	 */
	protected abstract suspend fun loginProfile(
		uuid: UUID,
		password: String,
	)

	fun login(
		uuid: UUID,
		password: String,
	) {
		viewModelScope.launch(Dispatchers.IO) {
			loginProfile(uuid,password)
		}
	}

	object PreviewVM: AbstractProfileLoginViewModel(MutableLiveData(listOf()), { id, pwd -> Unit}) {
		override suspend fun createProfile(username: String, password: String) {
			(profiles as MutableLiveData).value = profiles.value?.plus(
				EntProfileDao.EntProfileOverview(UUID.randomUUID(), username)
			)
		}

		override suspend fun loginProfile(uuid: UUID, password: String) {

		}
	}
}