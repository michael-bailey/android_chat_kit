package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.data_type.GlobalChatMessageData
import io.github.michael_bailey.android_chat_kit.data_type.UserChatMessageData
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IServerChatListViewModel
import io.github.michael_bailey.android_chat_kit.repository.MessageRepository
import io.github.michael_bailey.android_chat_kit.repository.ServerSocketRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

@ViewModelScoped
class ServerChatViewModel @Inject constructor(
	private val messageRepository: MessageRepository,
	private val serverSocketRepository: ServerSocketRepository
): IServerChatListViewModel {
	
	override val globalMessages: LiveData<List<GlobalChatMessageData>> = messageRepository
		.globalMessages.asLiveData()
	
	override val userMessages: LiveData<Map<UUID, Flow<List<UserChatMessageData>>>> = messageRepository.userMessages.asLiveData()
	
	suspend fun sendGlobalMessage(msg: String) {
		serverSocketRepository.sendGlobalMessage(msg)
	}
	
	suspend fun sendUserMessage(uuid: UUID, message: String) {
		serverSocketRepository.sendUserMessage(uuid, message)
	}
}
