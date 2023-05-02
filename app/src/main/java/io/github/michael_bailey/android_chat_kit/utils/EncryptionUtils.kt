package io.github.michael_bailey.android_chat_kit.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.Key
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.util.Base64
import javax.crypto.Cipher

/**
 * utilities for handling encryption.
 *
 * overall the master key should not leave this class
 */
object EncryptionUtils {
	private const val MASTER_KEY_NAME = "master"
	private const val ALGORITHM = "RSA/ECB/PKCS1Padding"

	/**
	 * fetches the apps master key
	 */
	private fun getMasterKey(): PrivateKey {

		// load key store
		val keyStore = KeyStore.getInstance("AndroidKeyStore")
		keyStore.load(null)

		val privateKeyEntry = keyStore.getEntry("my_key_alias", null) as KeyStore.PrivateKeyEntry?

		// return key if found
		if (privateKeyEntry != null) {
			return privateKeyEntry.privateKey
		}

		// create key generator
		val keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")

		keyPairGenerator.initialize(
			KeyGenParameterSpec.Builder(
				MASTER_KEY_NAME,
				KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
			)
				.setKeySize(2048)
				.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
				.build()
		)

		val keyPair = keyPairGenerator.generateKeyPair()
		return keyPair.private
	}

	private inline fun getEncryptCipher(key: Key): Cipher = Cipher.getInstance(ALGORITHM).apply { init(Cipher.ENCRYPT_MODE, key) }
	private inline fun getDecryptCipher(key: Key): Cipher = Cipher.getInstance(ALGORITHM).apply { init(Cipher.DECRYPT_MODE, key) }

	private fun encryptWithMasterKey(input: String): String {
		val cipher = getEncryptCipher(getMasterKey())

		return Base64.getEncoder()
			.encodeToString(cipher.doFinal(input.toByteArray()))
	}

	private fun decryptWithMasterKey(input: String): String {

		val base64 = Base64.getDecoder()
			.decode(input)

		val cipher = getDecryptCipher(getMasterKey())

		return String(cipher.doFinal(base64))
	}
}