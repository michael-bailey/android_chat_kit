package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import androidx.lifecycle.LiveData
import io.github.michael_bailey.android_chat_kit.data_type.GlobalChatMessageData
import io.github.michael_bailey.android_chat_kit.data_type.UserChatMessageData
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface IServerChatListViewModel {
	val globalMessages: LiveData<List<GlobalChatMessageData>>
	val userMessages: LiveData<Map<UUID, Flow<List<UserChatMessageData>>>>
}
