/*
 * Copyright (c) 2023.
 * This Software is Property of British Information Technologies.
 * All software that utilises or derives from this resource must link back to our source
 */

package org.british_information_technologies.chatkit_server_kotlin.lib.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class NetworkMessageOutput {
	
	@Serializable
	@SerialName("Request")
	data object Request : NetworkMessageOutput()
	
	@Serializable
	@SerialName("GotInfo")
	data class GotInfo(
		@Transient
		val hostname: String = "",
		val server_name: String,
		val server_owner: String,
	): NetworkMessageOutput()
	
	@Serializable
	@SerialName("Connecting")
	data object Connecting : NetworkMessageOutput()

	@Serializable
	@SerialName("Error")
	data object Error : NetworkMessageOutput()
}