package io.github.michael_bailey.android_chat_kit.interfaces.view_model

import androidx.lifecycle.LiveData
import java.util.UUID

interface IConnectedServerViewModel {
	val serverId: LiveData<UUID>
	val serverName: LiveData<String>
	val serverOwner: LiveData<String>
	val serverHostname: LiveData<String>
	
	suspend fun fetchInfo(hostname: String, port: Int)
}
