package com.example.claudio_garrido_20240608_continuidad_ing_informatica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imagenAdapter: ImagenAdapter
    private lateinit var autorTextView: TextView
    private val listaImagenes = listOf(
        Imagen("Obra 1", "https://artpassions.net/galleries/dore/destruction_of_leviathan.jpg"),
        Imagen("Obra 2", "https://cdn.britannica.com/75/147775-050-0825EFE0/Geraint-Enid-Tennyson-illustration-Meadow-Alfred-Gustave.jpg"),
        Imagen("Obra 3", "https://artpassions.net/galleries/dore/paradise_lost_13.jpg"),
        Imagen("Obra 4", "https://img.fruugo.com/product/1/00/14532001_max.jpg"),
        Imagen("Obra 5", "https://wmagazin.com/wp-content/uploads/2021/01/CL-ppal-GustaveDore-efemerides-Divinacomedia.jpg"),
        Imagen("Obra 6", "https://historia.nationalgeographic.com.es/medio/2022/01/17/litografia-de-el-infierno-de-dante-realizada-por-gustave-dore-en-1861_1a5316d7_800x631.jpg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autorTextView = findViewById(R.id.autorTextView)
        autorTextView.text = "Gustave Dore"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        imagenAdapter = ImagenAdapter(this, listaImagenes)
        recyclerView.adapter = imagenAdapter
    }
}
