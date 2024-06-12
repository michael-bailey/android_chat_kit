package io.github.michael_bailey.android_chat_kit.extension.context_wrapper

import android.app.NotificationManager
import android.content.Context

fun Context.notificationManager() = getSystemService(NotificationManager::class.java)