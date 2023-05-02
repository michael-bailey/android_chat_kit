package io.github.michael_bailey.gym_log_book.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class DateTimeConverter {
	@TypeConverter
	fun localDateTimeToLong(date: LocalDateTime): String = date.toString()

	@TypeConverter
	fun longToLocalDateTime(string: String): LocalDateTime = LocalDateTime.parse(string)
}