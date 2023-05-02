package io.github.michael_bailey.android_chat_kit.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.android_chat_kit.App
import io.github.michael_bailey.android_chat_kit.extension.application.preferences


class DebugPreferencesManager(
	private val app: App,

	private val _debugEnabled: MutableLiveData<Boolean> = MutableLiveData(
		app.preferences().getBoolean("debug_enabled", false)
	),
	private val _debugBottomNavBarEnabled: MutableLiveData<Boolean> =
		MutableLiveData(
			app.preferences()
				.getBoolean("debug_bottom_nav_bar_enabled", false)
		),

	private val _debugStatusBarColourEnabled: MutableLiveData<Boolean> =
		MutableLiveData(
			app.preferences()
				.getBoolean("debug_status_bar_colour_enabled", false)
		),

	private val _debugNavBarColourEnabled: MutableLiveData<Boolean> =
		MutableLiveData(
			app.preferences()
				.getBoolean("debug_nav_bar_colour_enabled", false)
		),
) {

	val isDebugEnabled: LiveData<Boolean> = _debugEnabled

	val isDebugBottomNavBarEnabled: LiveData<Boolean> =
		MediatorLiveData<Boolean>().apply {
			addSource(_debugEnabled) {
				value = _debugEnabled.value!! && _debugBottomNavBarEnabled.value!!
			}

			addSource(_debugBottomNavBarEnabled) {
				value = _debugEnabled.value!! && _debugBottomNavBarEnabled.value!!
			}
		}

	val isDebugStatusBarColourEnabled: LiveData<Boolean> =
		MediatorLiveData<Boolean>().apply {
			addSource(_debugEnabled) {
				value = _debugEnabled.value!! && _debugStatusBarColourEnabled.value!!
			}

			addSource(_debugStatusBarColourEnabled) {
				value = _debugEnabled.value!! && _debugStatusBarColourEnabled.value!!
			}
		}

	val isDebugNavBarColourEnabled: LiveData<Boolean> =
		MediatorLiveData<Boolean>().apply {
			addSource(_debugEnabled) {
				value = _debugEnabled.value!! && _debugNavBarColourEnabled.value!!
			}

			addSource(_debugNavBarColourEnabled) {
				value = _debugEnabled.value!! && _debugNavBarColourEnabled.value!!
			}
		}

	fun setIsDebugEnabled(value: Boolean) {
		app.preferences().edit().putBoolean("debug_enabled", value).apply()
		_debugEnabled.value = value
	}

	fun setIsDebugBottomNavBarEnabled(value: Boolean) {
		app.preferences().edit().putBoolean("debug_bottom_nav_bar_enabled", value)
			.apply()
		_debugBottomNavBarEnabled.value = value
	}

	fun setIsDebugStatusBarColourEnabled(value: Boolean) {
		app.preferences().edit()
			.putBoolean("debug_status_bar_colour_enabled", value).apply()
		_debugStatusBarColourEnabled.value = value
	}

	fun setIsDebugNavBarColourEnabled(value: Boolean) {
		app.preferences().edit().putBoolean("debug_nav_bar_colour_enabled", value)
			.apply()
		_debugNavBarColourEnabled.value = value
	}
}