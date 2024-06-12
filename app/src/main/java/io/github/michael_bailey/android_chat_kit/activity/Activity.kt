package io.github.michael_bailey.android_chat_kit.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * Custom activity class that embedds and sets up a view-model
 * to accept activity lifetime notifications.
 *
 * @author michael-bailey
 * @since 1.0
 */
abstract class Activity<VM>(
): ComponentActivity(
) where VM: ViewModel, VM: LifecycleObserver  {
	
	abstract val viewModel: VM
	
	/** adds the viewmodel to the activities observables */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
	}
	
	/** adds the viewmodel from the activities observables */
	override fun onDestroy() {
		super.onDestroy()
		lifecycle.removeObserver(viewModel)
	}
}