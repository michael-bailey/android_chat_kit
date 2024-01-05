package io.github.michael_bailey.android_chat_kit.extension.buffered_reader

import io.github.michael_bailey.android_chat_kit.extension.json.decodeNetworkMsg
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageOutput
import java.io.BufferedReader

suspend fun BufferedReader.genReadNetworkMsg(
): NetworkMessageOutput = Json.decodeNetworkMsg(this.genReadline())