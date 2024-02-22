package io.github.michael_bailey.android_chat_kit.repository

import android.content.SharedPreferences
import io.github.michael_bailey.android_chat_kit.preferences.preference_types.StringPreference
import io.github.michael_bailey.android_chat_kit.preferences.preference_types.UUIDPreference
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles all login state for teh applicaiton
 *
 * This should be injected onto a view model
 *
 * It is a global object.
 */
@Singleton
class LoginRepository @Inject constructor(
	pref: SharedPreferences
): Repository() {
	
	private val usernamePreference: StringPreference = StringPreference("username", pref)
	private val idPreference: UUIDPreference = UUIDPreference("uuid", pref)
	
	val hasProfile: Boolean
		get() = idPreference.fetch() != null && (
			usernamePreference.fetch()?.isNotEmpty() == true
		)
	
	val uuid by idPreference
	val username by usernamePreference
	
	init {
		scope.launch {
			// don't regen after reloading.
			if (idPreference.getValue() == null) {
				idPreference.update(UUID.randomUUID())
			}
		}
	}
	
	suspend fun setUsername(username: String) {
		usernamePreference.update(username)
	}
	
	suspend fun regenUUID() {
		idPreference.update(UUID.randomUUID())
	}
	
	fun genUsername() {
		usernamePreference._currentStateFlow.value
	}
	
	fun getUUID(): UUID? = idPreference.getValue()
	fun getUsername(): String = usernamePreference.getValue() ?: "Username"
}