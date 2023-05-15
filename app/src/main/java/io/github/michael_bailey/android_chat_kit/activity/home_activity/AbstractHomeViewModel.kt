package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

abstract class AbstractHomeViewModel(
	fetchToken: () -> Unit,
	protected var token: MutableLiveData<Pair<UUID, String>?> = MutableLiveData(null)
): ViewModel() {
	fun hasToken(): Boolean = token.value != null

	fun setProfileToken(token: Pair<UUID,String>?) { this.token.value = token }

	abstract fun onTokenSet()

}
