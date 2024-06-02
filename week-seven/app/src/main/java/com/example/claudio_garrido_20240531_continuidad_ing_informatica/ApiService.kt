package com.example.claudio_garrido_20240531_continuidad_ing_informatica

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("WS/getLocalesTurnos.php")
    fun getPharmacies(): Call<List<Pharmacy>>
}
