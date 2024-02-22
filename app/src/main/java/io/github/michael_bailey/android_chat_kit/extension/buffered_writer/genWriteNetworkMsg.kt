package io.github.michael_bailey.android_chat_kit.extension.buffered_writer

import io.github.michael_bailey.android_chat_kit.extension.json.encodeNetworkMsg
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.NetworkMessageInput
import java.io.BufferedWriter

suspend fun BufferedWriter.genWriteNetworkMsg(
	msg: NetworkMessageInput
) = this.genWriteline(Json.encodeNetworkMsg(msg))