package com.student.myapplication.labs.backgroundsync.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "Background sync task started...")
        delay(2000)
        Log.d("SyncWorker", "Background sync task completed successfully.")
        return Result.success()
    }
}