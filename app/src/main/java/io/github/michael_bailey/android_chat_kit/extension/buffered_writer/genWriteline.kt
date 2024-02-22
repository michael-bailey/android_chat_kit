package io.github.michael_bailey.android_chat_kit.extension.buffered_writer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedWriter

suspend inline fun BufferedWriter.genWriteline(string: String) = withContext(Dispatchers.IO) {
	write(string)
	newLine()
	flush()
}