package io.github.michael_bailey.android_chat_kit.extension.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageInput

fun Json.encodeClientMsg(
	msg: ClientMessageInput
): String = Json.encodeToString<ClientMessageInput>(msg)