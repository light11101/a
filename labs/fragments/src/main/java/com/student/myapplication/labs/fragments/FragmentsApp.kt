package com.student.myapplication.labs.fragments

import android.view.View
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragmentsApp() {
    val context = LocalContext.current
    val fragmentActivity = context as? FragmentActivity
    val containerId = remember { View.generateViewId() }
    var fragmentStatus by remember { mutableStateOf("No Fragment Attached") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fragments & Dynamic UI") },
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
                        text = "Dynamic FragmentContainerView",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Status: $fragmentStatus",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    AndroidView(
                        factory = { ctx ->
                            FragmentContainerView(ctx).apply {
                                id = containerId
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Fragment Transactions",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(
                        onClick = {
                            fragmentActivity?.supportFragmentManager?.let { fm ->
                                val fragment = DemoFragment.newInstance("Fragment A (Red)", 0xFFE53935)
                                fm.beginTransaction()
                                    .replace(containerId, fragment)
                                    .commit()
                                fragmentStatus = "Attached: Fragment A (Red)"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add / Replace Fragment A")
                    }

                    Button(
                        onClick = {
                            fragmentActivity?.supportFragmentManager?.let { fm ->
                                val fragment = DemoFragment.newInstance("Fragment B (Blue)", 0xFF1E88E5)
                                fm.beginTransaction()
                                    .replace(containerId, fragment)
                                    .commit()
                                fragmentStatus = "Attached: Fragment B (Blue)"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Replace with Fragment B")
                    }

                    OutlinedButton(
                        onClick = {
                            fragmentActivity?.supportFragmentManager?.let { fm ->
                                val fragment = fm.findFragmentById(containerId)
                                if (fragment != null) {
                                    fm.beginTransaction()
                                        .remove(fragment)
                                        .commit()
                                    fragmentStatus = "Fragment Removed"
                                } else {
                                    fragmentStatus = "No Fragment to Remove"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Remove Fragment")
                    }
                }
            }
        }
    }
}