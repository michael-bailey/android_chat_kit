package io.github.michael_bailey.android_chat_kit.repository

import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.utils.ClientDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject

@ViewModelScoped
class UserListRepository @Inject constructor(

) {
	private val _userList = MutableStateFlow(listOf<ClientDetails>())
	
	val userList: Flow<List<ClientDetails>> = _userList
	
	suspend fun updateList(clients: List<ClientDetails>) {
		_userList.emit(clients)
	}
	
	fun getUsername(sender: UUID): String? = _userList.value.find { it.uuid == sender }?.username
	
}
