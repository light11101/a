package com.student.myapplication.labs.productstore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class Product(val id: String, val name: String, val price: String)

val products = listOf(
    Product("p1", "Laptop", "$1000"),
    Product("p2", "Phone", "$800"),
    Product("p3", "Headphones", "$150")
)

@Composable
fun ProductStoreApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Product Store",
                    style = MaterialTheme.typography.headlineSmall
                )
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(products, key = { it.id }) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = item.price)
                            }
                            Button(onClick = { navController.navigate("detail/${item.id}") }) {
                                Text("Details")
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val product = products.find { it.id == id }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Product Details", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Product ID: $id")
                Text(text = "Name: ${product?.name ?: "Unknown"}")
                Text(text = "Price: ${product?.price ?: ""}")
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }
            }
        }
    }
}