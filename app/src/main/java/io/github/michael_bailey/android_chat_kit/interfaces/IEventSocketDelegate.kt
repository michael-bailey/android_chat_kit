package io.github.michael_bailey.android_chat_kit.interfaces

import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput

interface IEventSocketDelegate {
	suspend fun onMessageReceived(msg: ClientMessageOutput)
}
