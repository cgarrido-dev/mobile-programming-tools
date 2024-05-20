package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.Button

class ImageViewActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_IMAGE_PATH = "extra_image_path"

        fun newIntent(context: Context, imagePath: String): Intent {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_PATH, imagePath)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        val imageView: ImageView = findViewById(R.id.imageViewFullScreen)
        val imagePath = intent.getStringExtra(EXTRA_IMAGE_PATH)

        if (imagePath != null) {
            Glide.with(this).load(imagePath).into(imageView)
        }

        val backButton: Button = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}
