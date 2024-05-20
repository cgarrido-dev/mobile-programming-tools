package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class GraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val barChart: BarChart = findViewById(R.id.barChart)
        val entries = listOf(
            BarEntry(0f, 40f),
            BarEntry(1f, 35f),
            BarEntry(2f, 50f),
            BarEntry(3f, 45f),
            BarEntry(4f, 30f),
            BarEntry(5f, 55f)
        )
        val labels = listOf(
            "Claudio",
            "Cristian",
            "Juan",
            "Alicia",
            "Homero",
            "Ned"
        )
        val dataSet = BarDataSet(entries, "Viajes")
        val barData = BarData(dataSet)

        barChart.data = barData
        barChart.invalidate()

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = MyXAxisFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawLabels(true)
        xAxis.labelCount = labels.size
        xAxis.setAvoidFirstLastClipping(false)
        xAxis.isGranularityEnabled = true
        barChart.description.isEnabled = false

        val backButton: Button = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}
