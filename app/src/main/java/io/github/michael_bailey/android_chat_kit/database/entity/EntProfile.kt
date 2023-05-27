package io.github.michael_bailey.android_chat_kit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.michael_bailey.android_chat_kit.utils.PasswordUtils
import java.time.LocalDateTime
import java.util.UUID

@Entity(
	tableName = "profile",
	indices = []
)
class EntProfile(
	@PrimaryKey var uuid: UUID = UUID.randomUUID(),
	var createdTime: LocalDateTime = LocalDateTime.now(),
	
	val username: String,
	var salt: String,
	var password: String,
	
) {
	
	companion object {
		fun create(
			username: String,
			password: String,
			salt: String = PasswordUtils.generateSalt()
		): EntProfile = EntProfile(
			username = username,
			salt = salt,
			password = PasswordUtils.hashPassword(password, salt).getOrThrow(),
		)
	}
}