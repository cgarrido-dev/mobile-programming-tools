package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.view.animation.AnimationUtils

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val imageView: ImageView = findViewById(R.id.imageViewAnimation)
        val animation = AnimationUtils.loadAnimation(this, R.anim.transport_animation)
        imageView.startAnimation(animation)

        val backButton: Button = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}
