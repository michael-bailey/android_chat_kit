package io.github.michael_bailey.android_chat_kit.utils

open class SensitiveString(
	private val string: String
): Comparable<String> by string, CharSequence by string {
	override fun toString(): String {
		return "contents removed for protection"
	}
	
	fun getString(): String = string
}