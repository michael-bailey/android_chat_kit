package io.github.michael_bailey.android_chat_kit.activity.home_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import io.github.michael_bailey.android_chat_kit.data_type.ServerData
import io.github.michael_bailey.android_chat_kit.interfaces.view_model.IServersViewModel
import io.github.michael_bailey.android_chat_kit.repository.ServerInfoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServersViewModel @Inject constructor(
	private val serverInfoRepository: ServerInfoRepository,
): IServersViewModel {
	override val savedServers: LiveData<List<ServerData>>
		get() = serverInfoRepository.servers.asLiveData()
	
}