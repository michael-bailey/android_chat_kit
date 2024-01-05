package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import androidx.lifecycle.LiveData
import io.github.michael_bailey.android_chat_kit.interfaces.IMessageSendBarController

interface IConnectedServerViewModel
	: IChatListViewModel, IConnectedUserViewModel, IMessageSendBarController {
	val serverOwner: LiveData<String>
	val serverHostname: LiveData<String>
}
