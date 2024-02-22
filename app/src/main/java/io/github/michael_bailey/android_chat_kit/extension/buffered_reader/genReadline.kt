package io.github.michael_bailey.android_chat_kit.extension.buffered_reader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

suspend inline fun BufferedReader.genReadline() = withContext(Dispatchers.IO) { readLine() }