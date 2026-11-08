package com.student.myapplication.labs.announcements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    val isUrgent: Boolean = false
)

val sampleAnnouncements = listOf(
    Announcement(1, "Exam Schedule", "Practical exams begin next week.", isUrgent = true),
    Announcement(2, "Workshop Alert", "Android Compose workshop in Hall A.", isUrgent = false),
    Announcement(3, "Assignment Due", "Submit Assignment 2 by Friday.", isUrgent = true)
)

@Composable
fun AnnouncementsApp() {
    var statusText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Announcements App",
            style = MaterialTheme.typography.headlineSmall
        )

        if (statusText.isNotBlank()) {
            Text(text = statusText)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleAnnouncements, key = { it.id }) { item ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                        if (item.isUrgent) {
                            Text(text = "[URGENT]")
                        }
                    }
                    Text(text = item.content, style = MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = { statusText = "Acknowledged: ${item.title}" },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Acknowledge")
                    }
                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}