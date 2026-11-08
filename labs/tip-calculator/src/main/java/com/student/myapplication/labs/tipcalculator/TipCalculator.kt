package com.student.myapplication.labs.tipcalculator

object TipCalculator {
    fun calculateTip(bill: Double, tipPercent: Int): Double {
        if (bill <= 0.0) return 0.0
        return (bill * tipPercent) / 100.0
    }

    fun calculateTotal(bill: Double, tipPercent: Int): Double {
        if (bill <= 0.0) return 0.0
        return bill + calculateTip(bill, tipPercent)
    }
}