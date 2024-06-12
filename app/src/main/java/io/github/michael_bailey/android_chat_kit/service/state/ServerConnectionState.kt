package io.github.michael_bailey.android_chat_kit.service.state

sealed class ServerConnectionState(
	protected val delegate: IServerConnectionStateDelegate,
): IServerConnectionStateDelegate by delegate {}