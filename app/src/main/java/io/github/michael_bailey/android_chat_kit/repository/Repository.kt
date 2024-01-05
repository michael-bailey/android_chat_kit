package io.github.michael_bailey.android_chat_kit.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

abstract class Repository(protected val scope: CoroutineScope = GlobalScope)