package io.github.michael_bailey.gym_log_book.extension.any

import android.util.Log

inline fun Any.log(message: String) {
	Log.i(getTag(), message)
}

inline fun Any.getTag(): String = this::class.java.simpleName

