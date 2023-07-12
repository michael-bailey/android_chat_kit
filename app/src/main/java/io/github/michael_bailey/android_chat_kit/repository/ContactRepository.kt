package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.database.dao.EntContactDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(
	private val contactDao: EntContactDao
) {

}
