package io.github.michael_bailey.android_chat_kit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import io.github.michael_bailey.android_chat_kit.data_type.GlobalChatMessageData
import io.github.michael_bailey.android_chat_kit.data_type.UserChatMessageData
import io.github.michael_bailey.android_chat_kit.database.entity.EntGlobalChatMessage
import io.github.michael_bailey.android_chat_kit.database.entity.EntUserMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
	private val userListRepository: UserListRepository,
	private val loginRepository: LoginRepository,
) {
	private val _globalMessages = MutableStateFlow(listOf<EntGlobalChatMessage>())
	
	private val _userMessages = mutableMapOf<UUID, MutableStateFlow<List<EntUserMessage>>>()
	
	val globalMessages: Flow<List<GlobalChatMessageData>> = _globalMessages.combine(
		userListRepository.userList
	) { list, usr ->
		list.map { msg ->
			GlobalChatMessageData(
				senderId = msg.from,
				sender = usr.find { it.uuid == msg.from }?.name ?: "unknown",
				isReceived = loginRepository.getUUID() != msg.from,
				content = msg.content,
				received = LocalDateTime.now()
			)
		}.reversed()
	}
	
	val userMessages = userListRepository.userList.transform {list ->
		val uuidList = list.map { it.uuid }
		val list = _userMessages.filter { entry ->
			uuidList.contains(entry.key)
		}.mapValues { entry ->
			entry.value.map { msgList ->
				msgList.map { msg ->
					UserChatMessageData(
						senderId = msg.from,
						sender = list.find { it.uuid == msg.from }?.name ?: "unknown",
						isReceived = msg.from == loginRepository.getUUID(),
						received = msg.createdTime,
						content = msg.content,
					)
				}.reversed()
			}
		}
		emit(list)
	}
	
	fun getUserMessageStore(uuid: UUID): LiveData<List<UserChatMessageData>> {
		var messageFlow = _userMessages[uuid]
		
		if (messageFlow == null) {
			messageFlow = MutableStateFlow(listOf())
			_userMessages[uuid] = messageFlow
		}
		
		return messageFlow.combine(
			userListRepository.userList
		) { msgList, userList ->
			msgList.map { msg ->
				UserChatMessageData(
					senderId = msg.from,
					sender = userList.find { it.uuid == msg.from }?.name ?: "unknown",
					isReceived = msg.from != loginRepository.getUUID(),
					received = msg.createdTime,
					content = msg.content,
				)
			}.reversed()
		}.asLiveData()
	}
	
	suspend fun addUserMessage(
		from: UUID,
		content: String
	) {
		
		val message = EntUserMessage(
			from = from,
			to = loginRepository.getUUID()!!,
			content = content,
		)
		
		val messageList = _userMessages.get(from)
		
		if (messageList == null) {
			_userMessages[from] = MutableStateFlow(listOf(message))
		} else {
			messageList.emit(messageList.value+message)
		}
	}
	
	suspend fun addSentUserMessage(to: UUID, content: String) {
		val sentMessage = EntUserMessage(
			from = loginRepository.getUUID()!!,
			to = to,
			content = content,
		)
		
		val messageList = _userMessages.get(to)
		
		if (messageList == null) {
			_userMessages[to] = MutableStateFlow(listOf())
		} else {
			messageList.emit(messageList.value+sentMessage)
		}
	}
	
	suspend fun addGlobalMessage(msg: EntGlobalChatMessage) {
		_globalMessages.emit(_globalMessages.value + msg)
	}
	
	suspend fun update(messages: List<EntGlobalChatMessage>) {
		_globalMessages.emit(messages)
	}
	
	suspend fun clear() {
		_globalMessages.emit(listOf())
	}
}
