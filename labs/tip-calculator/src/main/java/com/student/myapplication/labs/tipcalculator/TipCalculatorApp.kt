package com.student.myapplication.labs.tipcalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp

@Composable
fun TipCalculatorApp() {
    var billInput by remember { mutableStateOf("") }
    var selectedPercent by remember { mutableStateOf(15) }

    val bill = billInput.toDoubleOrNull() ?: 0.0
    val tipAmount = TipCalculator.calculateTip(bill, selectedPercent)
    val totalBill = TipCalculator.calculateTotal(bill, selectedPercent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Tip Calculator App", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = billInput,
            onValueChange = { billInput = it },
            label = { Text("Bill Amount ($)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Select Tip Percentage:", style = MaterialTheme.typography.titleMedium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(10, 15, 20).forEach { percent ->
                if (selectedPercent == percent) {
                    Button(
                        onClick = { selectedPercent = percent },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("$percent%")
                    }
                } else {
                    OutlinedButton(
                        onClick = { selectedPercent = percent },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("$percent%")
                    }
                }
            }
        }

        HorizontalDivider()

        Text(text = "Tip Amount: $$tipAmount", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Total Bill: $$totalBill", style = MaterialTheme.typography.titleMedium)
    }
}