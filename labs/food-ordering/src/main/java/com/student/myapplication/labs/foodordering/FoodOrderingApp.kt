package com.student.myapplication.labs.foodordering

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class MenuItem(val name: String, val price: Double)

val foodMenu = listOf(
    MenuItem("Classic Burger", 8.99),
    MenuItem("Pepperoni Pizza", 12.50),
    MenuItem("Creamy Pasta", 9.99),
    MenuItem("Iced Cold Drink", 2.50)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingApp() {
    val context = LocalContext.current
    val selectedItems = remember { mutableStateMapOf<String, Boolean>() }
    var customerNote by remember { mutableStateOf("") }

    val totalPrice = selectedItems.entries
        .filter { it.value }
        .sumOf { entry -> foodMenu.find { it.name == entry.key }?.price ?: 0.0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Food Ordering App (Intents & Animations)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select Food Items:",
                style = MaterialTheme.typography.titleMedium
            )

            foodMenu.forEach { item ->
                val isChecked = selectedItems[item.name] ?: false
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = "$${item.price}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked -> selectedItems[item.name] = checked }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = customerNote,
                onValueChange = { customerNote = it },
                label = { Text("Special Instructions / Note") },
                modifier = Modifier.fillMaxWidth()
            )

            // Order Summary & Intent Trigger Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Total Price: $$totalPrice",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val itemsListStr = selectedItems.entries
                                .filter { it.value }
                                .joinToString(", ") { it.key }
                                .ifEmpty { "None" }

                            val intent = Intent(context, OrderSummaryActivity::class.java).apply {
                                putExtra(OrderSummaryActivity.EXTRA_ORDER_ITEMS, itemsListStr)
                                putExtra(OrderSummaryActivity.EXTRA_TOTAL_PRICE, totalPrice)
                                putExtra(OrderSummaryActivity.EXTRA_NOTE, customerNote)
                            }
                            context.startActivity(intent)
                        },
                        enabled = totalPrice > 0,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Place Order via Explicit Intent")
                    }
                }
            }
        }
    }
}