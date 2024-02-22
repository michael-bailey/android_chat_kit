package io.github.michael_bailey.android_chat_kit.extension.buffered_writer

import io.github.michael_bailey.android_chat_kit.extension.json.encodeClientMsg
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageInput
import java.io.BufferedWriter

suspend fun BufferedWriter.genWriteClientMsg(
	msg: ClientMessageInput
) = this.genWriteline(Json.encodeClientMsg(msg))