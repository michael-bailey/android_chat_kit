package io.github.michael_bailey.android_chat_kit.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.android_chat_kit.database.dao.EntContactDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntMessageDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseProvider {

	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"gym_log_book_db"
		).fallbackToDestructiveMigration().build()
	
	@Provides
	fun provideProfileDao(appDatabase: AppDatabase): EntProfileDao {
		return appDatabase.profileDao()
	}
	
	@Provides
	fun provideServerDao(appDatabase: AppDatabase): EntServerDao {
		return appDatabase.serverDao()
	}
	
	@Provides
	fun provideContactDao(appDatabase: AppDatabase): EntContactDao {
		return appDatabase.contactDao()
	}
	
	@Provides
	fun provideMessageDao(appDatabase: AppDatabase): EntMessageDao {
		return appDatabase.messageDao()
	}
}