package io.github.michael_bailey.android_chat_kit.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.android_chat_kit.database.dao.EntContactDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntServerDao
import io.github.michael_bailey.android_chat_kit.database.dao.EntUserMessageDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseProvider {

	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"chat-kit-db"
		).fallbackToDestructiveMigration().build()
	
	@Provides
	@Singleton
	fun provideServerDao(appDatabase: AppDatabase): EntServerDao {
		return appDatabase.serverDao()
	}
	
	@Provides
	@Singleton
	fun provideContactDao(appDatabase: AppDatabase): EntContactDao {
		return appDatabase.contactDao()
	}
	
	@Provides
	@Singleton
	fun provideMessageDao(appDatabase: AppDatabase): EntUserMessageDao {
		return appDatabase.messageDao()
	}
}