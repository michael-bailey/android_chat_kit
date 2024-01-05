package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import kotlinx.coroutines.Job

interface ISocketActorDelegate {
	fun receivedMessage(message: String): Job
	fun connectionDied(): Job
}