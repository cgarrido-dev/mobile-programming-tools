package com.example.claudio_garrido_20240531_continuidad_ing_informatica

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val name = sharedPreferences.getString("userName", null)

        if (name != null) {
            showWelcomeScreen(name)
        } else {
            showLoginScreen()
        }
    }

    private fun showLoginScreen() {
        setContentView(R.layout.activity_login)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            if (name.isEmpty()) {
                errorTextView.visibility = TextView.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    errorTextView.visibility = TextView.GONE
                }, 5000)
            } else {
                editor.putString("userName", name)
                editor.apply()
                showWelcomeScreen(name)
            }
        }
    }

    private fun showWelcomeScreen(name: String) {
        setContentView(R.layout.activity_welcome)
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Bienvenido: $name"

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            editor.clear()
            editor.apply()
            showLoginScreen()
        }

        fetchPharmacyData()
    }

    private fun fetchPharmacyData() {
        FetchPharmacyTask { pharmacies ->
            if (pharmacies != null) {
                val recyclerView = findViewById<RecyclerView>(R.id.pharmacyRecyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = PharmacyAdapter(pharmacies)
            }
        }.execute()
    }
}
