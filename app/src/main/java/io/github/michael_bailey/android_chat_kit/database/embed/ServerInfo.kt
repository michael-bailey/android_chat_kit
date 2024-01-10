package io.github.michael_bailey.android_chat_kit.database.embed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ServerInfo(
	// not used atm, this is updated by the dao
	var updatedTime: LocalDateTime = LocalDateTime.now(),
	
	var name: String,
	var owner: String,
	var alias: String,
): Parcelable