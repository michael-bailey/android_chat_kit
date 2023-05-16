package io.github.michael_bailey.android_chat_kit.delegates.interfaces

import android.content.Context
import androidx.room.Room
import io.github.michael_bailey.android_chat_kit.database.AppDatabase

class AppDatabaseDelegate(context: Context): IDatabaseDelegate<AppDatabase> {
	override val db: AppDatabase by lazy {
		Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"gym_log_book_db"
		).build()
	}
}