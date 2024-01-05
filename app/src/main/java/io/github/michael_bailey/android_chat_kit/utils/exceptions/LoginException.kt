package io.github.michael_bailey.android_chat_kit.utils.exceptions


sealed class LoginException(message: String): Exception(message) {
	class AlreadyLoggedInException: LoginException("There is already a profile logged in")
	class NotLoggedInException: LoginException("User not logged in")
}