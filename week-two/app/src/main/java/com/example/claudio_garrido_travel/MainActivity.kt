package com.example.claudio_garrido_travel

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val textView = findViewById<TextView>(R.id.dataTextView)
        textView.text = "$currentDate - Claudio Garrido"
    }
}