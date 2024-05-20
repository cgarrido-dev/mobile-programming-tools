package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import com.github.mikephil.charting.formatter.ValueFormatter

class MyXAxisFormatter(private val labels: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index >= 0 && index < labels.size) {
            labels[index]
        } else {
            ""
        }
    }
}
