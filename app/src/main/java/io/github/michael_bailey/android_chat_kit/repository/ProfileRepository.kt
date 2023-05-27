package io.github.michael_bailey.android_chat_kit.repository

import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import java.util.UUID
import javax.inject.Inject

class ProfileRepository @Inject constructor(
	private val profileDao: EntProfileDao,
) {
	suspend fun getProfileOverview(uuid: UUID): EntProfileDao.EntProfileOverview =
		profileDao.queryProfileOverview(uuid)
}