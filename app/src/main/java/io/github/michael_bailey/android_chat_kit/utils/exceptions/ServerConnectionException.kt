package io.github.michael_bailey.android_chat_kit.utils.exceptions

sealed class ServerConnectionException(
	msg: String
): Exception(msg) {
	class ServerFailedToConnect: ServerConnectionException("Could not connect to server")
	class ServerFailedToGetInfo: ServerConnectionException("Info request to server failed")
	class ServerDidNotSendRequest : ServerConnectionException("Server did not send request")
	class ServerDidNotGetInfo : ServerConnectionException("Server did not send get info")
}
