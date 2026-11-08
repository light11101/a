package com.student.myapplication.labs.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class AnnouncementType { NORMAL, URGENT, ICON, ACTION }

data class Announcement(
    val id: Int,
    val title: String,
    val date: String,
    val content: String,
    val type: AnnouncementType,
    val actionText: String? = null
)

val sampleAnnouncements = listOf(
    Announcement(1, "Lab Exam Schedule Released", "Today, 10:00 AM", "The practical exams begin next Monday. Check the portal for timings.", AnnouncementType.URGENT),
    Announcement(2, "Workshop on Jetpack Compose", "Yesterday", "Join us in Hall B for an interactive Android workshop.", AnnouncementType.ICON),
    Announcement(3, "Submit Assignment 2", "2 days ago", "Please upload your project zip file to the portal before Friday.", AnnouncementType.ACTION, "Submit Now"),
    Announcement(4, "Library Hours Extended", "3 days ago", "The central library will remain open until 10 PM during exam week.", AnnouncementType.NORMAL)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsApp() {
    var actionMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements (Dynamic UI)") },
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
        ) {
            if (actionMessage.isNotBlank()) {
                Text(
                    text = actionMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(sampleAnnouncements, key = { it.id }) { item ->
                    AnnouncementCard(
                        announcement = item,
                        onActionClick = {
                            actionMessage = "Action triggered for: '${item.title}'"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AnnouncementCard(
    announcement: Announcement,
    onActionClick: () -> Unit
) {
    val cardColor = when (announcement.type) {
        AnnouncementType.URGENT -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (announcement.type == AnnouncementType.ICON) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📢", color = Color.White)
                    }
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                }

                Text(
                    text = announcement.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = announcement.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = announcement.content, style = MaterialTheme.typography.bodyMedium)

            if (announcement.type == AnnouncementType.ACTION && announcement.actionText != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onActionClick) {
                    Text(announcement.actionText)
                }
            }
        }
    }
}