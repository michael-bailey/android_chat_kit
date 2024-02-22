package io.github.michael_bailey.android_chat_kit.utils

import io.github.michael_bailey.android_chat_kit.utils.exceptions.PasswordUtilException
import org.mindrot.jbcrypt.BCrypt
import org.mindrot.jbcrypt.BCrypt.checkpw
import org.mindrot.jbcrypt.BCrypt.hashpw

/**
 * A utilities object for handling password operations
 */
object PasswordUtils {
	private const val SALT_LENGTH = 16
	private const val HASH_ITERATIONS = 12

	/**
	 * generated a salt for bcrypt
	 */
	fun generateSalt(): String {
		return BCrypt.gensalt(4)
	}

	/**
	 * validates a password
	 *
	 * This includes:
	 * - 3 words delimitated by one of ' ', '-', '_'
	 * - be longer than 16 characters
	 */
	fun validatePassword(password: String): Result<Unit> = runCatching {
		var spaceSplit = password.split(' ', ignoreCase = false, limit = 99).count() < 3
		var dashSplit = password.split('-', ignoreCase = false, limit = 99).count() < 3
		var underscoreSplit = password.split('_', ignoreCase = false, limit = 99).count() < 3

		var length = password.count() < 16

		when {
			spaceSplit || dashSplit || underscoreSplit -> throw PasswordUtilException.PasswordNotThreeWordsException()
			length -> throw PasswordUtilException.PasswordTooShortException()
		}
	}

	/**
	 * hashes a password using a salt
	 */
	fun hashPassword(
		password: String,
		salt: String,
	): Result<String> = runCatching {
		hashpw(password, salt)
	}
	
	/**
	 * compares plain text password to hashed password
	 */
	fun checkPassword(
		password: Password,
		hashedPassword: String,
	): Boolean {
		return checkpw(password.getString(), hashedPassword)
	}
	
	/**
	 * Get the exception that would be thrown if login was attempted and failed
	 */
	fun getPasswordErrors(password: String): PasswordUtilException? {
		val result = validatePassword(password)
		return if (result.isSuccess) return null else result.exceptionOrNull() as PasswordUtilException
	}

}