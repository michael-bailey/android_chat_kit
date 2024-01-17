package io.github.michael_bailey.android_chat_kit.data_type.sharable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Representation of a servers information.
 */
@Serializable
@Parcelize
data class SharableServerInfoData(
	val hostname: String,
	val port: Int = 5600,
	val name: String,
	val owner: String,
): Parcelable
