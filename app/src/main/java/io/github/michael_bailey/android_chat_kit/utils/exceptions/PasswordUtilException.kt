package io.github.michael_bailey.android_chat_kit.utils.exceptions

sealed class PasswordUtilException(message: String?) : Exception(message) {
	class PasswordIncorrectException: PasswordUtilException("Entered Incorrect Password")
	class PasswordTooShortException: PasswordUtilException("Entered Password Should Contain at least 16 characters")
	class PasswordNotThreeWordsException: PasswordUtilException("Entered Password Should Be At Least Three Words")
}