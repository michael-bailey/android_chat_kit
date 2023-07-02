package io.github.michael_bailey.android_chat_kit.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProviderModule {
	
	@Provides
	@Singleton
	fun provideTokenRepository(
		@ApplicationContext context: Context,
		profileDao: EntProfileDao,
		preferences: SharedPreferences
	): TokenRepository = TokenRepository(
		profileDao = profileDao,
		preferences = preferences
	)
	
	@Provides
	fun provideProfileRepository(
		profileDao: EntProfileDao,
	): ProfileRepository = ProfileRepository(
		profileDao = profileDao
	)
	
	@Provides
	fun provideLoginRepository(
		@ApplicationContext context: Context,
		tokenRepository: TokenRepository,
		profileRepository: ProfileRepository
	): LoginRepository = LoginRepository(
		tokenRepository = tokenRepository,
		profileRepository = profileRepository,
	)
	
	@Provides
	fun provideServerRepository(
	): ServerRepository = ServerRepository(
	)
}