package com.example.claudio_garrido_20240608_continuidad_ing_informatica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imagenAdapter: ImagenAdapter
    private lateinit var autorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autorTextView = findViewById(R.id.autorTextView)
        autorTextView.text = "Gustabe Dore"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Dos columnas
        imagenAdapter = ImagenAdapter(this, listOf())
        recyclerView.adapter = imagenAdapter

        ImageRepository.images.observe(this, Observer { imagenes ->
            imagenAdapter.actualizarLista(imagenes)
        })

        val workRequest = OneTimeWorkRequestBuilder<ActualizarImagenesWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}
