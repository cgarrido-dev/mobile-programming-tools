package com.example.claudio_garrido_20240522_continuidad_ing_informatica

data class AttendanceRecord(
    val id: Long = 0,
    val rut: String,
    val name: String,
    val lastname: String,
    val dateTime: String,
    val type: String
)

