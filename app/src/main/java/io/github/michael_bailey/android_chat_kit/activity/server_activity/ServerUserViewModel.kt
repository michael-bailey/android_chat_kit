package io.github.michael_bailey.android_chat_kit.activity.server_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IConnectedUserViewModel
import io.github.michael_bailey.android_chat_kit.repository.LoginRepository
import java.util.UUID
import javax.inject.Inject

@ViewModelScoped
class ServerUserViewModel @Inject constructor(
	private val loginRepository: LoginRepository,
): IConnectedUserViewModel {
	override val userId: LiveData<UUID?> = loginRepository.uuid.asLiveData()
	override val username: LiveData<String?> = loginRepository.username.asLiveData()
}