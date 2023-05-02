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
class EntProfile(val username: String, password: String) {

	@PrimaryKey var uuid: UUID = UUID.randomUUID()
	var createdTime: LocalDateTime = LocalDateTime.now()

	var salt: String = PasswordUtils.generateSalt()
	var password: String

	init {
		this.salt = PasswordUtils.generateSalt()
		this.password = PasswordUtils.hashPassword(password, salt)
	}
}