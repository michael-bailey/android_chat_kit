/*
 * Copyright (c) 2023.
 * This Software is Property of British Information Technologies.
 * All software that utilises or derives from this resource must link back to our source
 */

package org.british_information_technologies.chatkit_server_kotlin.lib

import io.github.michael_bailey.android_chat_kit.extension.any.log
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class Weak<T>(
	initial: T? = null
) {

	private var value: WeakReference<T>? = initial?.let { WeakReference(it) }

	operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = value?.get()

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
		log("set wek reference")
		this.value = WeakReference(value)
	}
}