package com.example.claudio_garrido_20240608_continuidad_ing_informatica

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ImageRepository {
    private val _images = MutableLiveData<List<Imagen>>(listOf(
        Imagen("Obra 1", "https://artpassions.net/galleries/dore/destruction_of_leviathan.jpg"),
        Imagen("Obra 2", "https://cdn.britannica.com/75/147775-050-0825EFE0/Geraint-Enid-Tennyson-illustration-Meadow-Alfred-Gustave.jpg"),
        Imagen("Obra 3", "https://artpassions.net/galleries/dore/paradise_lost_13.jpg"),
        Imagen("Obra 4", "https://img.fruugo.com/product/1/00/14532001_max.jpg"),
        Imagen("Obra 5", "https://wmagazin.com/wp-content/uploads/2021/01/CL-ppal-GustaveDore-efemerides-Divinacomedia.jpg"),
        Imagen("Obra 6", "https://historia.nationalgeographic.com.es/medio/2022/01/17/litografia-de-el-infierno-de-dante-realizada-por-gustave-dore-en-1861_1a5316d7_800x631.jpg")
    ))
    val images: LiveData<List<Imagen>> get() = _images

    fun addImages(newImages: List<Imagen>) {
        val currentImages = _images.value?.toMutableList() ?: mutableListOf()
        currentImages.addAll(newImages)
        _images.postValue(currentImages)
    }
}
