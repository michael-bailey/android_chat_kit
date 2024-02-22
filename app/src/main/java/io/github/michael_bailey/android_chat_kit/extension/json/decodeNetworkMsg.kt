package io.github.michael_bailey.android_chat_kit.extension.json

import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageOutput

fun Json.decodeNetworkMsg(
	msg: String
): NetworkMessageOutput = Json.decodeFromString<NetworkMessageOutput>(msg)