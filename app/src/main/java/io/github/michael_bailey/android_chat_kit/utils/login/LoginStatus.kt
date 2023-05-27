package io.github.michael_bailey.android_chat_kit.utils.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class LoginStatus() : Parcelable {

	@Parcelize
	object LoggedOut: LoginStatus()
	
	@Parcelize
	object LoggedIn: LoginStatus()
}