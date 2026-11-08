package com.student.myapplication.labs.shoppingcart

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CartCalculatorTest {
    @Test
    fun testSubtotalCalculation() {
        val items = listOf(
            CartItem("Mouse", 20.0, 2),
            CartItem("Keyboard", 50.0, 1)
        )
        val subtotal = CartCalculator.calculateSubtotal(items)
        assertEquals(90.0, subtotal, 0.001)
    }

    @Test
    fun testDiscountCodeSAVE10() {
        val subtotal = 100.0
        val finalTotal = CartCalculator.calculateFinalTotal(subtotal, "SAVE10")
        assertEquals(90.0, finalTotal, 0.001)
    }

    @Test
    fun testInvalidDiscountCode() {
        assertFalse(CartCalculator.isValidCode("WRONGCODE"))
        assertTrue(CartCalculator.isValidCode("SAVE10"))
    }
}