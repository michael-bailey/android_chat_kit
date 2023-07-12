package io.github.michael_bailey.android_chat_kit.messages

abstract class Message {
	val type: String = this::class.java.simpleName
}