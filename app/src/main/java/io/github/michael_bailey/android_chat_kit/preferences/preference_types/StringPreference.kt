package io.github.michael_bailey.android_chat_kit.preferences.preference_types

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class StringPreference(
	val name: String,
	sharedPreferences: SharedPreferences
): PreferenceController<String>(
	name = name,
	sharedPreferences = sharedPreferences
) {
	override fun getValue(pref: SharedPreferences): String? = pref.getString(name, null)
	override fun setValue(editor: Editor, value: String?): Editor = editor.putString(name, value)
}