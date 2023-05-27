package io.github.michael_bailey.android_chat_kit.repository

import android.content.SharedPreferences
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import io.github.michael_bailey.android_chat_kit.utils.PasswordUtils
import io.github.michael_bailey.android_chat_kit.utils.exceptions.PasswordUtilException
import java.util.UUID
import javax.inject.Inject

typealias Token = Pair<UUID, String>

/**
 * This class handles the storage generation and fetching of tokens
 * Tokens are used for fetching more information about a profile.
 */
class TokenRepository @Inject constructor(
	private val profileDao: EntProfileDao,
	private val preferences: SharedPreferences,
) {
	suspend fun getToken(uuid: UUID, password: String): Result<Token> = runCatching {
		val profile = profileDao.loadProfile(uuid, password).getOrThrow()
		profile.uuid to profile.password
	}
	
	suspend fun createAndGetToken(username: String, password: String): Result<Pair<UUID, String>> = runCatching{
		val profile = EntProfile.create(username, password)
		profileDao.insertProfile(profile)
		profile.uuid to profile.password
	}
	
	fun getPasswordErrors(password: String): PasswordUtilException? {
		val result = PasswordUtils.validatePassword(password)
		return if (result.isSuccess) return null else result.exceptionOrNull() as PasswordUtilException
	}
	
	fun getToken(): Token? {
		val uuid = preferences.getString(uuid_key, null)
		val hash = preferences.getString(hash_key, null)
		
		if (uuid == null || hash == null) return null
		
		return UUID.fromString(uuid) to hash
	}
	
	fun saveToken(token: Token) {
		preferences.edit()
			.putString(uuid_key, token.first.toString())
			.apply()
		
		preferences.edit()
			.putString(hash_key, token.second)
			.commit()
	}
	
	fun clearToken() {
		preferences.edit()
			.putString(uuid_key, null)
			.apply()
		
		preferences.edit()
			.putString(hash_key, null)
			.commit()
	}
	
	
	companion object {
		private const val uuid_key = "logged_in_uuid"
		private const val hash_key = "logged_in_hash"
	}
}