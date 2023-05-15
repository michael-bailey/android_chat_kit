package io.github.michael_bailey.chatkitandroid

import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import io.github.michael_bailey.android_chat_kit.utils.PasswordUtils
import org.junit.Test

class EntProfileTest {
	val name = "name"
	val password = "Password123456"
	
	@Test
	fun createProfile() {
		val ent = EntProfile(
			username = name,
			password = password
		)
		
		val hashedPassword= ent.password
		
		assert(PasswordUtils.checkPassword(
			password = password,
			hashedPassword = hashedPassword
		))
	}
}