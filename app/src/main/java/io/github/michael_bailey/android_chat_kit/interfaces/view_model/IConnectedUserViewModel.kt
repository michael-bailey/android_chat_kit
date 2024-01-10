package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import androidx.lifecycle.LiveData
import java.util.UUID

interface IConnectedUserViewModel {
	val userId: LiveData<UUID?>
	val username: LiveData<String?>
}
