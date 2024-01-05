package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import androidx.lifecycle.LiveData

interface IProfileViewModel {
	val uuid: LiveData<String>
	val username: LiveData<String>
}