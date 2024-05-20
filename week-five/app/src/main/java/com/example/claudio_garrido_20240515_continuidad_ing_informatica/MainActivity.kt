package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animationButton: Button = findViewById(R.id.buttonAnimation)
        val videoButton: Button = findViewById(R.id.buttonVideo)
        val driversButton: Button = findViewById(R.id.buttonDrivers)
        val capturePhotoButton: Button = findViewById(R.id.buttonCapturePhoto)
        val graphButton: Button = findViewById(R.id.buttonGraph)

        animationButton.setOnClickListener {
            startActivity(Intent(this, AnimationActivity::class.java))
        }
        videoButton.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }
        driversButton.setOnClickListener {
            startActivity(Intent(this, DriversActivity::class.java))
        }
        capturePhotoButton.setOnClickListener {
            startActivity(Intent(this, CapturePhotoActivity::class.java))
        }
        graphButton.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }
    }
}