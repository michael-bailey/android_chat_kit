package io.github.michael_bailey.android_chat_kit.activity.connected_server_activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.repository.ContactRepository
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.PlainConnectedServerRepository
import javax.inject.Inject

@HiltViewModel
class ConnectedServerViewModel @Inject constructor(
	private val contactRepository: ContactRepository,
	private val messageRepository: MessageRepository,
): ViewModel() {
	
	private lateinit var connectedServerRepository: PlainConnectedServerRepository
	
	init {
	
	}
	
}