package io.github.michael_bailey.android_chat_kit.preferences.preference_types

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import java.util.UUID

class UUIDPreference(
	val name: String,
	sharedPreferences: SharedPreferences
): PreferenceController<UUID>(
	name = name,
	sharedPreferences = sharedPreferences
) {
	override fun getValue(pref: SharedPreferences): UUID? = pref.getString(name, null)
		.let { if (it != null) {UUID.fromString(it)} else null }
	
	override fun setValue(editor: Editor, value: UUID?): Editor = editor.putString(name, value.toString())
}