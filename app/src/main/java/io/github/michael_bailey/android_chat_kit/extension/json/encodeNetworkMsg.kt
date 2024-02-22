package io.github.michael_bailey.android_chat_kit.extension.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageInput

fun Json.encodeNetworkMsg(
	msg: NetworkMessageInput
): String = Json.encodeToString<NetworkMessageInput>(msg)