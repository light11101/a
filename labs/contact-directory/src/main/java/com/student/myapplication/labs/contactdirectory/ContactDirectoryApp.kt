package com.student.myapplication.labs.contactdirectory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class Employee(
    val id: Int,
    val name: String,
    val designation: String,
    val phone: String
)

val employeeList = listOf(
    Employee(1, "Alice Smith", "Senior Android Developer", "+1 555-0101"),
    Employee(2, "Bob Johnson", "UI/UX Designer", "+1 555-0102"),
    Employee(3, "Charlie Brown", "Backend Engineer", "+1 555-0103"),
    Employee(4, "Diana Prince", "QA Lead", "+1 555-0104"),
    Employee(5, "Evan Wright", "DevOps Specialist", "+1 555-0105"),
    Employee(6, "Fiona Gallagher", "Product Manager", "+1 555-0106")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDirectoryApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Directory (LazyColumn & Snackbar)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(employeeList, key = { it.id }) { employee ->
                EmployeeRow(
                    employee = employee,
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Selected: ${employee.name} (${employee.phone})"
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EmployeeRow(
    employee: Employee,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "👤",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = employee.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = employee.designation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = employee.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}