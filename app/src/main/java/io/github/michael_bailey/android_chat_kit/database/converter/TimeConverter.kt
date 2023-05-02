package io.github.michael_bailey.gym_log_book.database.converter

import androidx.room.TypeConverter
import java.time.LocalTime


class TimeConverter {
	@TypeConverter
	fun localTimeToLong(time: LocalTime): Long = time.toNanoOfDay()

	@TypeConverter
	fun longToLocalTime(long: Long): LocalTime = LocalTime.ofNanoOfDay(long)
}