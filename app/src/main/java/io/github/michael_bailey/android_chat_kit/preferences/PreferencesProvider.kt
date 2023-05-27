package io.github.michael_bailey.android_chat_kit.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.android_chat_kit.utils.EncryptionUtils
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesProvider {
	
//	@Provides
//	@Singleton
//	fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
//		(context as Application).preferences()
	
	@Provides
	@Singleton
	fun provideEncryptedPreferences(@ApplicationContext context: Context): SharedPreferences =
		EncryptedSharedPreferences.create(
			"EncryptedPreferences",
			EncryptionUtils.mainKeyAlias,
			context,
			EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
			EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
		)
}