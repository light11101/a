package com.student.myapplication.labs.feedbackcollector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FeedbackItem(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val comment: String,
    val timestamp: String = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
)

@Composable
fun FeedbackCollectorApp() {
    val feedbackList = remember { mutableStateListOf<FeedbackItem>() }
    var nameInput by remember { mutableStateOf("") }
    var commentInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Feedback Collector App", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Student Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = commentInput,
            onValueChange = { commentInput = it },
            label = { Text("Feedback Comment") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nameInput.isNotBlank() && commentInput.isNotBlank()) {
                    feedbackList.add(0, FeedbackItem(name = nameInput, comment = commentInput))
                    nameInput = ""
                    commentInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Feedback")
        }

        HorizontalDivider()

        Text(
            text = "Saved Feedback (${feedbackList.size})",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(feedbackList, key = { it.id }) { item ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = item.comment, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Submitted at: ${item.timestamp}", style = MaterialTheme.typography.bodySmall)
                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}