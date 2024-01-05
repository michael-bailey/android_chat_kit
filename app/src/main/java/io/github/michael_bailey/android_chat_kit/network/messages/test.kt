package io.github.michael_bailey.android_chat_kit.network.messages

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
sealed class Response

@kotlinx.serialization.Serializable
object EmptyResponse : Response()

@Serializable
class TextResponse(val text: String) : Response()