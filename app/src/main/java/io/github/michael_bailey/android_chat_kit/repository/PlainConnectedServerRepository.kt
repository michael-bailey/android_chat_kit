package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.repository.interfaces.IConnectedServerRepository
import io.github.michael_bailey.android_chat_kit.utils.ClientDetails
import io.github.michael_bailey.android_chat_kit.utils.network.ServerConnectionSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlainConnectedServerRepository(

	private val profileDetails: ClientDetails,
	private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
): IConnectedServerRepository {
	
	@Inject lateinit var contactRepository: ContactRepository
	@Inject lateinit var chatThreadRepository: ChatThreadRepository
	@Inject lateinit var messageRepository: MessageRepository
	
	private val connectionStatus = MutableStateFlow(ServerConnectionSession.ConnectionStatus.Disconnected)
	
	val connectionState: Flow<ServerConnectionSession.ConnectionStatus> = connectionStatus
		
	init {

	}
}
