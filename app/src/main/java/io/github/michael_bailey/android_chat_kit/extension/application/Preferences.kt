package io.github.michael_bailey.android_chat_kit.extension.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

fun Application.preferences(): SharedPreferences = getSharedPreferences(
	"Settings",
	Context.MODE_PRIVATE
)