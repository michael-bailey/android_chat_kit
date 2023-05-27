package io.github.michael_bailey.android_chat_kit.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.michael_bailey.android_chat_kit.database.AppDatabase
import io.github.michael_bailey.android_chat_kit.delegates.AppDatabaseDelegate
import io.github.michael_bailey.android_chat_kit.delegates.interfaces.IDatabaseDelegate

class ServerStatusWorker(
	context: Context,
	workerParams: WorkerParameters,
) : Worker(
	context,
	workerParams
), IDatabaseDelegate<AppDatabase> by AppDatabaseDelegate(
	context = context
) {
	override fun doWork(): Result {
		return Result.failure()
	}
}