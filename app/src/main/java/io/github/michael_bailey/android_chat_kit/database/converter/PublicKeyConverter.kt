package io.github.michael_bailey.android_chat_kit.database.converter

import androidx.room.TypeConverter
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64


class PublicKeyConverter {
	@TypeConverter
	fun publicKeyToString(key: PublicKey?): String? = key?.let {
		Base64.getEncoder().encode(it.encoded).toString()
	}
	
	@TypeConverter
	fun stringToPublicKey(string: String?): PublicKey? = string?.let {
		KeyFactory.getInstance("RSA")
			.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(it)))
	}
}