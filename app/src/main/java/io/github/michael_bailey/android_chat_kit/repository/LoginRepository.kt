package io.github.michael_bailey.android_chat_kit.repository

import java.util.UUID
import javax.inject.Inject

/**
 * Handles all login state for teh applicaiton
 *
 * This should be injected onto a view model
 *
 * It is a global object.
 */
class LoginRepository @Inject constructor(
	private val tokenRepository: TokenRepository,
	private val profileRepository: ProfileRepository
) {
	
	/**
	 * token pair of the logged in user
	 */
	private val token: Pair<UUID, String>? get() = tokenRepository.getToken()
	
	/**
	 * Tells if the user is logged in
	 */
	val isLoggedIn: Boolean get() = token != null
	
	/**
	 * sets loginToken
	 */
	fun setLoginToken(token: Pair<UUID, String>) {
		tokenRepository.saveToken(token)
	}
	
	/**
	 * Logs a profile out of the application
	 */
	fun logout() {
		tokenRepository.clearToken()
	}

}