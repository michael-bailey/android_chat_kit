package io.github.michael_bailey.android_chat_kit.repository

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
	private val preferences: SharedPreferences
): Repository() {

}