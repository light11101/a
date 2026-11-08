package com.student.myapplication.labs.backgroundsync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.student.myapplication.labs.backgroundsync.worker.SyncWorker
import kotlinx.coroutines.flow.map
import java.util.UUID
import java.util.concurrent.TimeUnit

private const val PERIODIC_WORK = "periodic_sync"

@Composable
fun BackgroundSyncApp() {
    val context = LocalContext.current
    val workManager = remember { WorkManager.getInstance(context) }

    var lastOneTimeId by remember { mutableStateOf<UUID?>(null) }

    val oneTimeStatusFlow = remember(lastOneTimeId) {
        lastOneTimeId?.let { id ->
            workManager.getWorkInfoByIdFlow(id).map { it?.state?.name ?: "IDLE" }
        }
    }
    val oneTimeStatus by (oneTimeStatusFlow?.collectAsState(initial = "ENQUEUED") ?: remember { mutableStateOf("IDLE") })

    val periodicStatusFlow = remember(workManager) {
        workManager.getWorkInfosForUniqueWorkFlow(PERIODIC_WORK)
            .map { list -> list.firstOrNull()?.state?.name ?: "IDLE" }
    }
    val periodicStatus by periodicStatusFlow.collectAsState(initial = "IDLE")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Background Sync (WorkManager)",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(text = "One-Time Work Status: $oneTimeStatus")

        Button(
            onClick = {
                val req = OneTimeWorkRequestBuilder<SyncWorker>().build()
                lastOneTimeId = req.id
                workManager.enqueue(req)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Run One-Time Sync (Instant 1-sec)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Periodic Work Status: $periodicStatus")

        Button(
            onClick = {
                val periodicReq = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES).build()
                workManager.enqueueUniquePeriodicWork(
                    PERIODIC_WORK,
                    ExistingPeriodicWorkPolicy.UPDATE,
                    periodicReq
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enqueue Periodic Sync")
        }

        OutlinedButton(
            onClick = {
                workManager.cancelUniqueWork(PERIODIC_WORK)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel Periodic Work")
        }
    }
}