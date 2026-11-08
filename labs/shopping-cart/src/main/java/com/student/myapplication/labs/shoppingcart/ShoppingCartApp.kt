package com.student.myapplication.labs.shoppingcart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp

data class ProductInfo(val id: Int, val name: String, val price: Double)

val storeProducts = listOf(
    ProductInfo(1, "Wireless Mouse", 20.0),
    ProductInfo(2, "Keyboard", 50.0),
    ProductInfo(3, "USB Drive", 10.0)
)

@Composable
fun ShoppingCartApp() {
    val quantities = remember { mutableStateMapOf<Int, Int>(1 to 0, 2 to 0, 3 to 0) }
    var discountCodeInput by remember { mutableStateOf("") }

    val cartItems = storeProducts.map { p ->
        CartItem(p.name, p.price, quantities[p.id] ?: 0)
    }

    val subtotal = CartCalculator.calculateSubtotal(cartItems)
    val isValid = CartCalculator.isValidCode(discountCodeInput)
    val finalTotal = CartCalculator.calculateFinalTotal(subtotal, discountCodeInput)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Shopping Cart App", style = MaterialTheme.typography.headlineSmall)

        storeProducts.forEach { product ->
            val qty = quantities[product.id] ?: 0
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "$${product.price}")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { if (qty > 0) quantities[product.id] = qty - 1 }) {
                        Text("-")
                    }
                    Text(text = "$qty", style = MaterialTheme.typography.titleMedium)
                    Button(onClick = { quantities[product.id] = qty + 1 }) {
                        Text("+")
                    }
                }
            }
            HorizontalDivider()
        }

        Text(text = "Subtotal: $$subtotal", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = discountCodeInput,
            onValueChange = { discountCodeInput = it },
            label = { Text("Discount Code (e.g. SAVE10)") },
            modifier = Modifier.fillMaxWidth()
        )

        if (discountCodeInput.isNotBlank()) {
            if (isValid) {
                Text(text = "Discount Applied! (10% OFF)")
            } else {
                Text(text = "Invalid code", color = MaterialTheme.colorScheme.error)
            }
        }

        Text(
            text = "Total Payable: $$finalTotal",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}