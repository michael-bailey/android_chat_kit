package io.github.michael_bailey.android_chat_kit.extension.buffered_reader

import io.github.michael_bailey.android_chat_kit.extension.json.decodeClientMsg
import kotlinx.serialization.json.Json
import org.british_information_technologies.chatkit_server_kotlin.lib.messages.ClientMessageOutput
import java.io.BufferedReader

suspend fun BufferedReader.genReadClientMsg(
): ClientMessageOutput = Json.decodeClientMsg(this.genReadline())