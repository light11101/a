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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
    MenuItem("Burger", 10.0),
    MenuItem("Pizza", 15.0),
    MenuItem("Drink", 5.0)
)

@Composable
fun FoodOrderingApp() {
    val context = LocalContext.current
    val selectedItems = remember { mutableStateMapOf<String, Boolean>() }
    var noteInput by remember { mutableStateOf("") }

    val totalPrice = selectedItems.entries
        .filter { it.value }
        .sumOf { entry -> foodMenu.find { it.name == entry.key }?.price ?: 0.0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Food Ordering App",
            style = MaterialTheme.typography.headlineSmall
        )

        foodMenu.forEach { item ->
            val isChecked = selectedItems[item.name] ?: false
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${item.name} - $${item.price.toInt()}")
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked -> selectedItems[item.name] = checked }
                )
            }
            HorizontalDivider()
        }

        OutlinedTextField(
            value = noteInput,
            onValueChange = { noteInput = it },
            label = { Text("Note / Instructions") },
            modifier = Modifier.fillMaxWidth()
        )

        Column(modifier = Modifier.animateContentSize()) {
            Text(
                text = "Total Price: $$totalPrice",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val itemsStr = selectedItems.entries
                        .filter { it.value }
                        .joinToString(", ") { it.key }
                        .ifEmpty { "None" }

                    val intent = Intent(context, OrderSummaryActivity::class.java).apply {
                        putExtra("order_items", itemsStr)
                        putExtra("total_price", totalPrice)
                        putExtra("note", noteInput)
                    }
                    context.startActivity(intent)
                },
                enabled = totalPrice > 0,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Place Order")
            }
        }
    }
}