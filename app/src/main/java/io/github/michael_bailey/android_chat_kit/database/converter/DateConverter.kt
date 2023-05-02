package io.github.michael_bailey.gym_log_book.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate


class DateConverter {
	@TypeConverter
	fun localDateToLong(date: LocalDate): Long = date.toEpochDay()

	@TypeConverter
	fun longToLocalDate(long: Long): LocalDate = LocalDate.ofEpochDay(long)
}