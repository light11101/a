package com.student.myapplication.labs.tipcalculator

import org.junit.Assert.assertEquals
import org.junit.Test

class TipCalculatorTest {
    @Test
    fun testCalculateTip() {
        val tip = TipCalculator.calculateTip(100.0, 15)
        assertEquals(15.0, tip, 0.001)
    }

    @Test
    fun testCalculateTotal() {
        val total = TipCalculator.calculateTotal(100.0, 15)
        assertEquals(115.0, total, 0.001)
    }
}