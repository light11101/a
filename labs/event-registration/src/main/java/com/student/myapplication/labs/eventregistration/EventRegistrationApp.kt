package com.student.myapplication.labs.eventregistration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

data class RegistrationRecord(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val email: String,
    val event: String,
    val time: String = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
)

val availableEvents = listOf("Hackathon", "Workshop", "Quiz")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventRegistrationApp() {
    val registrations = remember { mutableStateListOf<RegistrationRecord>() }
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var selectedEvent by remember { mutableStateOf(availableEvents.first()) }
    var expandedDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Event Registration App", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Total Registrations: ${registrations.size}", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Participant Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expandedDropdown,
            onExpandedChange = { expandedDropdown = !expandedDropdown },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedEvent,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Event") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedDropdown,
                onDismissRequest = { expandedDropdown = false }
            ) {
                availableEvents.forEach { ev ->
                    DropdownMenuItem(
                        text = { Text(ev) },
                        onClick = {
                            selectedEvent = ev
                            expandedDropdown = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (nameInput.isNotBlank() && emailInput.isNotBlank()) {
                    registrations.add(0, RegistrationRecord(name = nameInput, email = emailInput, event = selectedEvent))
                    nameInput = ""
                    emailInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(registrations, key = { it.id }) { rec ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "${rec.name} (${rec.email})", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Event: ${rec.event} | Time: ${rec.time}", style = MaterialTheme.typography.bodyMedium)
                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}