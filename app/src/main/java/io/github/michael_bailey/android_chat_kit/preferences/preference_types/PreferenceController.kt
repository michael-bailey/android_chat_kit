package io.github.michael_bailey.android_chat_kit.preferences.preference_types

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import io.github.michael_bailey.android_chat_kit.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

abstract class PreferenceController<T>(
	private val name: String,
	private val sharedPreferences: SharedPreferences
): Repository(), SharedPreferences.OnSharedPreferenceChangeListener {
	
	val _currentStateFlow: MutableStateFlow<T?> = MutableStateFlow(null)
	
	init {
		sharedPreferences.registerOnSharedPreferenceChangeListener(this)
		scope.launch { _currentStateFlow.emit(getValue(sharedPreferences)) }
	}
	
	suspend fun update(value: T?) {
		setValue(sharedPreferences.edit(), value).apply()
	}
	
	fun fetch(): T? = getValue(sharedPreferences)
	
	operator fun getValue(thisRef: Any?, property: KProperty<*>): Flow<T?> {
		return _currentStateFlow
	}
	
	protected abstract fun getValue(pref: SharedPreferences): T?
	
	protected abstract fun setValue(editor: Editor, value: T?): Editor
	
	override fun onSharedPreferenceChanged(pref: SharedPreferences, key: String?) {
		if (key == name) {
			scope.launch(Dispatchers.IO) { _currentStateFlow.emit(getValue(pref)) }
		}
	}
	
	/**
	 * returns the current value instantly
	 */
	fun getValue(): T? = getValue(sharedPreferences)
}