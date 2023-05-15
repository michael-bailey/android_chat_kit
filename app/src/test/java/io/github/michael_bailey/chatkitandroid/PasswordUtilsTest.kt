package io.github.michael_bailey.chatkitandroid

import io.github.michael_bailey.android_chat_kit.utils.PasswordUtils
import org.junit.Test

class PasswordUtilsTest {
	val password = "testPassword"
	val salt = PasswordUtils.generateSalt()
	
	
	@Test
	fun createPasswordAndCheck() {
		val res = PasswordUtils.hashPassword(
			password = password,
			salt = salt
		
		)
		assert(res.isSuccess)
		val hashedPassword = res.getOrThrow()
		
		val passwordCheck = PasswordUtils.checkPassword(
			password = password,
			hashedPassword = hashedPassword
		)
		
		assert(passwordCheck)
	}
}