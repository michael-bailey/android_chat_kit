package io.github.michael_bailey.android_chat_kit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import io.github.michael_bailey.android_chat_kit.utils.PasswordUtils
import io.github.michael_bailey.android_chat_kit.utils.exceptions.PasswordUtilException
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 *
 * It contains an overview version of the profile ent
 * this allows hiding details of the ent from the user without auth
 *
 * the private methods will create true versions of EntProfile
 * so they are protected to prevent other classes from accessing it
 */
abstract class EntProfileDao {

	data class EntProfileOverview(
		val uuid: UUID,
		val username: String,
	)

	@Query(
		"""
			SELECT uuid, username FROM profile
			ORDER BY createdTime
		"""
	)
	abstract fun queryProfileOverviews(): LiveData<List<EntProfileOverview>>

	@Query(
		"""
			SELECT uuid, username FROM profile
			WHERE uuid == :uuid
			LIMIT 1
		"""
	)
	abstract fun genQueryProfileOverview(uuid: UUID): LiveData<EntProfileOverview>

	@Query(
		"""
			SELECT uuid, username FROM profile
			WHERE uuid == :uuid
			LIMIT 1
		"""
	)
	abstract suspend fun queryProfileOverview(uuid: UUID): EntProfileOverview


	@Query(
		"""
			SELECT * FROM profile
			WHERE uuid == :uuid
			LIMIT 1
		"""
	)
	protected abstract suspend fun queryProfile(uuid: UUID): EntProfile

	suspend fun loadProfile(
		uuid: UUID,
		password: String
	): Result<EntProfile> = runCatching {
		val profile = queryProfile(uuid)
		val isPasswordCorrect = PasswordUtils.checkPassword(password, profile.password)

		if (!isPasswordCorrect) {
			throw PasswordUtilException.PasswordIncorrectException()
		}

		profile
	}
	
	suspend fun loadProfileFromHash(
		uuid: UUID,
		password: String
	): Result<EntProfile> = runCatching {
		val profile = queryProfile(uuid)
		val isPasswordSame = profile.password == password
		
		if (!isPasswordSame) {
			throw PasswordUtilException.PasswordIncorrectException()
		}
		
		profile
	}

	@Insert
	abstract suspend fun insertProfile(profile: EntProfile)

	@Update
	abstract suspend fun updateTask(profile: EntProfile)

	@Delete
	abstract suspend fun deleteTask(profile: EntProfile)
}