package com.student.myapplication.labs.firebaseauth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun FirebaseAuthApp() {
    var loggedInUserEmail by remember { mutableStateOf<String?>(null) }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Auth & Protected Screen", style = MaterialTheme.typography.headlineSmall)

        if (loggedInUserEmail != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Protected Home Screen", style = MaterialTheme.typography.titleMedium)
                Text(text = "Logged in as: $loggedInUserEmail")
                Button(
                    onClick = {
                        loggedInUserEmail = null
                        statusMessage = "Logged out successfully"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        } else {
            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (statusMessage.isNotBlank()) {
                Text(text = statusMessage)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (emailInput.contains("@") && passwordInput.length >= 6) {
                            loggedInUserEmail = emailInput
                            statusMessage = ""
                        } else {
                            statusMessage = "Enter a valid email and 6+ char password"
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Login")
                }

                OutlinedButton(
                    onClick = {
                        if (emailInput.contains("@") && passwordInput.length >= 6) {
                            loggedInUserEmail = emailInput
                            statusMessage = ""
                        } else {
                            statusMessage = "Enter a valid email and 6+ char password"
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Register")
                }
            }
        }
    }
}