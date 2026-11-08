package com.student.myapplication.labs.backgroundsync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.student.myapplication.labs.backgroundsync.worker.SyncWorker
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

private const val UNIQUE_PERIODIC_WORK = "lab_periodic_sync"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundSyncApp() {
    val context = LocalContext.current
    val workManager = remember { WorkManager.getInstance(context) }

    val periodicStatusFlow = remember(workManager) {
        workManager.getWorkInfosForUniqueWorkFlow(UNIQUE_PERIODIC_WORK)
            .map { list -> list.firstOrNull()?.state?.name ?: "NOT_ENQUEUED" }
    }
    val periodicStatus by periodicStatusFlow.collectAsState(initial = "NOT_ENQUEUED")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Background Sync (WorkManager)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. One-Time Work Request",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val oneTimeRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()
                            workManager.enqueue(oneTimeRequest)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enqueue One-Time Sync Work")
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. Periodic Work Request",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Periodic work minimum interval is 15 minutes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Work State: $periodicStatus",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val periodicRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES).build()
                            workManager.enqueueUniquePeriodicWork(
                                UNIQUE_PERIODIC_WORK,
                                ExistingPeriodicWorkPolicy.UPDATE,
                                periodicRequest
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enqueue Periodic Sync (15 Min)")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            workManager.cancelUniqueWork(UNIQUE_PERIODIC_WORK)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel Periodic Work")
                    }
                }
            }
        }
    }
}