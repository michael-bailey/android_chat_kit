package io.github.michael_bailey.android_chat_kit.utils.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LoginStatus() : Parcelable {

	@Parcelize
	object LoggedOut: LoginStatus(), Parcelable
	
	@Parcelize
	object LoggedIn: LoginStatus(), Parcelable
}