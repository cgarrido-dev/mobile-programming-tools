package com.example.claudio_garrido_20240501_continuidad_ing_informatica

import android.os.Bundle
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var responsiblePerson: EditText
    private lateinit var numberOfPeople: EditText
    private lateinit var dateEditText: EditText
    private lateinit var showReservationButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        responsiblePerson = findViewById(R.id.responsiblePerson)
        numberOfPeople = findViewById(R.id.numberOfPeople)
        dateEditText = findViewById(R.id.dateEditText)
        showReservationButton = findViewById(R.id.showReservationButton)

        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                dateEditText.setText("${selectedDay}/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)

            datePickerDialog.show()
        }

        showReservationButton.setOnClickListener {
            if (responsiblePerson.text.trim().isEmpty() || numberOfPeople.text.trim().isEmpty() || dateEditText.text.trim().isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos para la reserva.", Toast.LENGTH_LONG).show()
            } else {
                val message = "Reserva: ${responsiblePerson.text}, ${numberOfPeople.text}, ${dateEditText.text}"
                Log.d("ReservationInfo trace", message)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ReservationInfo trace", "App Iniciada")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ReservationInfo trace", "App Detenida")
    }

}