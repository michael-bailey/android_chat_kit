package io.github.michael_bailey.android_chat_kit.interfaces

import kotlinx.coroutines.Job

interface IMessageSendBarController {
	fun sendGlobalMessage(msg: String): Job
}
