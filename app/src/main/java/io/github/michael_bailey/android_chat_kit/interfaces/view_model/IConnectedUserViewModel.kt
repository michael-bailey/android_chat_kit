package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import androidx.lifecycle.LiveData
import io.github.michael_bailey.android_chat_kit.utils.ClientDetails

interface IConnectedUserViewModel {
	
	val users: LiveData<List<ClientDetails>>
}
