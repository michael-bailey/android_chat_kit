package io.github.michael_bailey.android_chat_kit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.michael_bailey.android_chat_kit.database.converter.PublicKeyConverter
import io.github.michael_bailey.android_chat_kit.database.dao.EntContactDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntMessageDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.edge.EdgeChatThreadToMessage
import io.github.michael_bailey.android_chat_kit.database.edge.EdgeChatThreadToUser
import io.github.michael_bailey.android_chat_kit.database.edge.EdgeThreadToMessage
import io.github.michael_bailey.android_chat_kit.database.entity.EntChatThread
import io.github.michael_bailey.android_chat_kit.database.entity.EntContact
import io.github.michael_bailey.android_chat_kit.database.entity.EntMessage
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile
import io.github.michael_bailey.android_chat_kit.database.entity.EntServer
import io.github.michael_bailey.android_chat_kit.database.entity.EntThread
import io.github.michael_bailey.gym_log_book.database.converter.DateConverter
import io.github.michael_bailey.gym_log_book.database.converter.DateTimeConverter
import io.github.michael_bailey.gym_log_book.database.converter.TimeConverter
import io.github.michael_bailey.gym_log_book.database.converter.UUIDConverter

@Database(
	entities = [
	EntProfile::class,
	EntServer::class,
	EntMessage::class,
	EntContact::class,
	EntChatThread::class,
	EntThread::class,
	EdgeChatThreadToMessage::class,
	EdgeThreadToMessage::class,
	EdgeChatThreadToUser::class,
 	],
	version = 3,
	exportSchema = true
)
@TypeConverters(
	DateTimeConverter::class,
	DateConverter::class,
	TimeConverter::class,
	UUIDConverter::class,
	PublicKeyConverter::class
)
abstract class AppDatabase: RoomDatabase() {
	abstract fun profileDao(): EntProfileDao
	abstract fun serverDao(): EntServerDao
	abstract fun messageDao(): EntMessageDao
	abstract fun contactDao(): EntContactDao
}