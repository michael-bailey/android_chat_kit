package io.github.michael_bailey.android_chat_kit.repository

import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.data_type.GlobalChatMessage
import io.github.michael_bailey.android_chat_kit.database.entity.EntGlobalChatMessage
import io.github.michael_bailey.android_chat_kit.extension.any.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transform
import java.time.LocalDateTime
import javax.inject.Inject

@ViewModelScoped
class MessageRepository @Inject constructor(
	userListRepository: UserListRepository,
	loginRepository: LoginRepository,
) {
	private val _globalMessage = MutableStateFlow(listOf<EntGlobalChatMessage>())
	
	
	
	var groupedEntGlobalMessages: Flow<List<List<EntGlobalChatMessage>>> = _globalMessage.transform { list ->
		
		val groupedList = mutableListOf<List<EntGlobalChatMessage>>()
		
		var currentGroup = mutableListOf<EntGlobalChatMessage>()
		
		var previous: EntGlobalChatMessage? = null
		
		var count = 0
		
		for (msg in list) {
			count += 1
			
			log("${previous?.sender == msg.sender}")
			
			if (previous == null || previous.sender == msg.sender) {
				log("adding to current group")
				currentGroup.add(msg)
				previous = msg
				continue
			} else {
				log("Creating new group")
				groupedList.add(currentGroup)
				currentGroup = mutableListOf(msg)
				previous = msg
				continue
			}
			
			log("aparently something went wrong here")
		}
		
		groupedList.add(currentGroup)
		
		log("$count")
		log("${groupedList.flatten().count()}")
		log("${currentGroup.count()}")
		
		emit(groupedList)
	}
	
	val globalMessages: Flow<List<List<GlobalChatMessage>>> = groupedEntGlobalMessages.combine(
		userListRepository.userList
	) { msg, usr ->
		msg.map { list ->
			list.map { msg ->
				GlobalChatMessage(
					sender = usr.find { it.uuid == msg.sender }?.username ?: "unknown",
					isReceived = loginRepository.getUUID() != msg.sender,
					content = msg.content,
					received = LocalDateTime.now()
				)
			}
		}
	}
	

	
	suspend fun addMessage(msg: EntGlobalChatMessage) {
		_globalMessage.emit(_globalMessage.value + msg)
	}
	
	suspend fun update(messages: List<EntGlobalChatMessage>) {
		_globalMessage.emit(messages)
	}
}
