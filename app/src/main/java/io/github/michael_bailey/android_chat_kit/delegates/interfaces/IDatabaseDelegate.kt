package io.github.michael_bailey.android_chat_kit.delegates.interfaces

import androidx.room.RoomDatabase

interface IDatabaseDelegate<D: RoomDatabase> {
	val db: D
}