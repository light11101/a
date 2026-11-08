package com.student.myapplication.labs.shoppingcart

data class CartItem(val name: String, val price: Double, val quantity: Int)

object CartCalculator {
    fun calculateSubtotal(items: List<CartItem>): Double {
        return items.sumOf { it.price * it.quantity }
    }

    fun calculateFinalTotal(subtotal: Double, discountCode: String): Double {
        if (discountCode.trim().uppercase() == "SAVE10") {
            return subtotal * 0.90
        }
        return subtotal
    }

    fun isValidCode(discountCode: String): Boolean {
        if (discountCode.isBlank()) return true
        return discountCode.trim().uppercase() == "SAVE10"
    }
}