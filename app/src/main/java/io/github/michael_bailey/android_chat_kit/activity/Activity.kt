package io.github.michael_bailey.android_chat_kit.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

abstract class Activity<VM>(
): ComponentActivity(
) where VM: ViewModel, VM: LifecycleObserver  {
	
	protected abstract val vm: VM
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(vm)
	}
	
	override fun onDestroy() {
		super.onDestroy()
		lifecycle.removeObserver(vm)
	}
}