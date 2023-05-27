package io.github.michael_bailey.android_chat_kit.broadcasters

import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class BroadcastProvider {
	
	
	@Provides
//	@Singleton
	fun LocalBroadcastManagerProvider(
		@ApplicationContext ctx: Context
	): LocalBroadcastManager = LocalBroadcastManager.getInstance(ctx)
	
}