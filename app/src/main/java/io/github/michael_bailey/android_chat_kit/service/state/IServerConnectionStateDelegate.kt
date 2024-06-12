package io.github.michael_bailey.android_chat_kit.service.state

/**
 * This interface defined lifecycle events for Server connection state
 *
 */
interface IServerConnectionStateDelegate {
	
	/**
	 * Called when state is connecting
	 */
	fun onConnecting(state: ServerConnectionStateConnecting)
	
	fun onConnectingFailed(state: ServerConnectionStateDisconnected)
	
	fun onConnected(state: ServerConnectionStateConnected)
	
	fun onConnectionFailed(state: ServerConnectionStateDisconnected)
	
	fun onDisconnected(state: ServerConnectionStateDisconnected)
	
}
