package io.github.michael_bailey.android_chat_kit.extension.list

import io.github.michael_bailey.android_chat_kit.extension.any.log

inline fun <T> List<T>.runningGroupBy(
	selector: (T) -> Any
): List<List<T>> {
	val groupedList = mutableListOf<List<T>>()
	
	var currentGroup = mutableListOf<T>()
	
	var previous: T? = null
	
	var count = 0
	
	for (msg in this) {
		count += 1
		
		if (previous == null || selector(previous) == selector(msg)) {
			log("adding to current group")
			currentGroup.add(msg)
			previous = msg
			continue
		} else {
			log("Creating new group")
			groupedList.add(currentGroup)
			currentGroup = mutableListOf(msg)
			previous = msg
			continue
		}
	}
	
	groupedList.add(currentGroup)
	
	log("$count")
	log("${groupedList.flatten().count()}")
	log("${currentGroup.count()}")
	
	return groupedList.toList()
}