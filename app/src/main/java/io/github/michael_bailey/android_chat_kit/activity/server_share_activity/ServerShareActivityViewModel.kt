package io.github.michael_bailey.android_chat_kit.activity.server_share_activity

import android.media.Image
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.activity.Activity
import io.github.michael_bailey.android_chat_kit.extension.view_model.gen
import io.github.michael_bailey.android_chat_kit.repository.ServerRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ServerShareActivityViewModel @Inject constructor(
	private val serverRepository: ServerRepository
): ViewModel(), DefaultLifecycleObserver {
	
	val QRCode: LiveData<Image?> = flow {
		emit(null)
	}.asLiveData()
	
	override fun onCreate(owner: LifecycleOwner) = gen {
		super.onCreate(owner)
		
		owner as Activity<*>
		
		val intent = owner.intent
		
		val server_uuid = intent.extras?.getString("uuid")
		
		if (server_uuid == null) {
			owner.finish()
			return@gen
		}
	}
}
