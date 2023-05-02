package io.github.michael_bailey.gym_log_book.extension.component_activity

import android.app.Application
import io.github.michael_bailey.gym_log_book.extension.application.preferences

inline fun Application.isDebugEnabled() = preferences().let {
	it.getBoolean("debug_enabled", false)
}

inline fun Application.setIsDebugEnabled(value: Boolean) = preferences().let {
	it.edit().putBoolean("debug_enabled", value).apply()
}

inline fun Application.isDebugBottomNavBarEnabled() = preferences().let {
	it.getBoolean("debug_bottom_nav_bar_enabled", false) && isDebugEnabled()
}

inline fun Application.setIsDebugBottomNavBarEnabled(value: Boolean) =
	preferences().let {
		it.edit().putBoolean("debug_bottom_nav_bar_enabled", value).apply()
	}

inline fun Application.isDebugStatusBarColourEnabled() = preferences().let {
	it.getBoolean("debug_status_bar_colour_enabled", false) && isDebugEnabled()
}

inline fun Application.setIsDebugStatusBarColourEnabled(value: Boolean) =
	preferences().let {
		it.edit().putBoolean("debug_status_bar_colour_enabled", value).apply()
	}

inline fun Application.isDebugNavBarColourEnabled() = preferences().let {
	it.getBoolean("debug_nav_bar_colour_enabled", false) && isDebugEnabled()
}

inline fun Application.setIsDebugNavBarColourEnabled(value: Boolean) =
	preferences().let {
		it.edit().putBoolean("debug_nav_bar_colour_enabled", value).apply()
	}