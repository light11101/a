package com.student.myapplication.labs.foodordering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class OrderSummaryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val items = intent.getStringExtra("order_items") ?: "None"
        val total = intent.getDoubleExtra("total_price", 0.0)
        val note = intent.getStringExtra("note") ?: ""

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Order Summary",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = "Items: $items")
                    Text(text = "Total: $$total")
                    if (note.isNotBlank()) {
                        Text(text = "Note: $note")
                    }
                    Button(onClick = { finish() }) {
                        Text("Back")
                    }
                }
            }
        }
    }
}