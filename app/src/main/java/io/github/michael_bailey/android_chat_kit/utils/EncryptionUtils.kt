package io.github.michael_bailey.android_chat_kit.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
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
	private fun getMasterPrivateKey(): PrivateKey {

		// load key store
		val keyStore = KeyStore.getInstance("AndroidKeyStore")
		keyStore.load(null)

		val privateKeyEntry = keyStore.getEntry("my_key_alias", null) as KeyStore.PrivateKeyEntry?

		// return key if found
		if (privateKeyEntry != null) {
			return privateKeyEntry.privateKey
		}

		return generateMasterKey().private
	}

	private fun getMasterPublicKey(): PublicKey {
		// load key store
		val keyStore = KeyStore.getInstance("AndroidKeyStore")
		keyStore.load(null)

		val privateKeyEntry = keyStore.getEntry("my_key_alias", null) as KeyStore.PrivateKeyEntry?

		// return key if found
		if (privateKeyEntry != null) {
			return privateKeyEntry.certificate.publicKey
		}

		return generateMasterKey().public
	}

	private fun generateMasterKey(): KeyPair {
		// create key generator
		val keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")

		keyPairGenerator.initialize(
			KeyGenParameterSpec.Builder(
				MASTER_KEY_NAME,
				KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
			)
				.setKeySize(2048)
				.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
				.build()
		)

		return keyPairGenerator.generateKeyPair()
	}


	private inline fun getEncryptCipher(key: PublicKey): Cipher = Cipher.getInstance(ALGORITHM).apply { init(Cipher.ENCRYPT_MODE, key) }
	private inline fun getDecryptCipher(key: PrivateKey): Cipher = Cipher.getInstance(ALGORITHM).apply { init(Cipher.DECRYPT_MODE, key) }

	private inline fun getSigner(key: PrivateKey) = Signature.getInstance("SHA256withECDSA").apply {
		initSign(key)
	}

	private inline fun getVerifier(key: PublicKey) = Signature.getInstance("SHA256withECDSA").apply {
		initVerify(key)
	}


	fun encryptWithMasterKey(input: String): String {
		val cipher = getEncryptCipher(getMasterPublicKey())

		return Base64.getEncoder()
			.encodeToString(cipher.doFinal(input.toByteArray()))
	}

	private fun decryptWithMasterKey(input: String): String =
		getDecryptCipher(getMasterPrivateKey())
			.run { String(doFinal(Base64.getDecoder().decode(input))) }


	fun signWithMasterKey(input: String): String = getSigner(getMasterPrivateKey())
		.run {
			update(input.toByteArray())
			String(Base64.getEncoder().encode(sign()))
		}

	fun verifyWithMasterKey(input: String, signature: String): Boolean = getVerifier(
		getMasterPublicKey()
	).run {
		update(input.toByteArray())
		verify(signature.toByteArray())
	}
}