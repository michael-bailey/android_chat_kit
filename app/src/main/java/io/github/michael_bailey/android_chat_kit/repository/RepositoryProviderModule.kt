package io.github.michael_bailey.android_chat_kit.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryProviderModule {
	
	@Binds
	abstract fun bindServerRepository(
		serverRepository: MutableServerRepository
	): ServerRepository
}