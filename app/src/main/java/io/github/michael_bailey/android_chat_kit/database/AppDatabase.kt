package io.github.michael_bailey.android_chat_kit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.michael_bailey.android_chat_kit.database.converter.PublicKeyConverter
import io.github.michael_bailey.android_chat_kit.database.dao.EntContactDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntUserMessageDao
import io.github.michael_bailey.android_chat_kit.database.edge.EdgeChatThreadToMessage
import io.github.michael_bailey.android_chat_kit.database.edge.EdgeThreadToMessage
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import io.github.michael_bailey.android_chat_kit.database.entity.EntThread
import io.github.michael_bailey.android_chat_kit.database.entity.EntUser
import io.github.michael_bailey.android_chat_kit.database.entity.EntUserMessage
import io.github.michael_bailey.gym_log_book.database.converter.DateConverter
import io.github.michael_bailey.gym_log_book.database.converter.DateTimeConverter
import io.github.michael_bailey.gym_log_book.database.converter.TimeConverter
import io.github.michael_bailey.gym_log_book.database.converter.UUIDConverter

@Database(
	entities = [
		EntServer::class,
		EntUserMessage::class,
		EntUser::class,
		EntThread::class,
		EdgeChatThreadToMessage::class,
		EdgeThreadToMessage::class,
 	],
	version = 7,
	exportSchema = true,
	autoMigrations = [
	]
)
@TypeConverters(
	DateTimeConverter::class,
	DateConverter::class,
	TimeConverter::class,
	UUIDConverter::class,
	PublicKeyConverter::class
)
abstract class AppDatabase: RoomDatabase() {
	abstract fun serverDao(): EntServerDao
	abstract fun messageDao(): EntUserMessageDao
	abstract fun contactDao(): EntContactDao
}