package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.data_type.ConnectedUserData
import io.github.michael_bailey.android_chat_kit.utils.ClientDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListRepository @Inject constructor(
): Repository() {
	private val _userList = MutableStateFlow(listOf<ClientDetails>())
	
	val userList: Flow<List<ConnectedUserData>> = _userList.map { list ->
		list.map {
			ConnectedUserData(
				uuid = it.uuid,
				name = it.username,
			)
		}
	}
	
	suspend fun updateList(clients: List<ClientDetails>) {
		_userList.emit(clients)
	}
	
	fun getUsername(sender: UUID): String? = _userList.value.find { it.uuid == sender }?.username
	suspend fun clear() {
		_userList.emit(listOf())
	}
	
}
