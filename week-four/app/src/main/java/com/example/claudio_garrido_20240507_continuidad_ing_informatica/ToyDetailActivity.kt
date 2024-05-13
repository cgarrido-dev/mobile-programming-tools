package com.example.claudio_garrido_20240507_continuidad_ing_informatica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button

class ToyDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toy_detail)

        val toyId = intent.getIntExtra("TOY_ID", -1)
        val toy = ToyData.toyList.find { it.id == toyId }

        val toyDetailImageView: ImageView = findViewById(R.id.toyDetailImageView)
        val toyDetailNameTextView: TextView = findViewById(R.id.toyDetailNameTextView)
        val toyDetailPriceTextView: TextView = findViewById(R.id.toyDetailPriceTextView)
        val backButton: Button = findViewById(R.id.backButton)

        toy?.let {
            toyDetailImageView.setImageResource(it.image)
            toyDetailNameTextView.text = it.name
            toyDetailPriceTextView.text = "$${it.price}"
        }

        backButton.setOnClickListener { finish() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}