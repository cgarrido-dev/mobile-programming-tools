package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button

class DriversActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewDrivers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DriversAdapter(getDrivers())

        val backButton: Button = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun getDrivers(): List<Driver> {
        return listOf(
            Driver("Claudio Garrido", R.drawable.driver1),
            Driver("Cristian Garrido", R.drawable.driver2),
            Driver("Juan Perez", R.drawable.driver3),
            Driver("Alicia Fernandez", R.drawable.driver4),
            Driver("Homero Simpsons", R.drawable.driver5),
            Driver("Ned Flanders", R.drawable.driver6)
        )
    }
}

data class Driver(val name: String, val photo: Int)

class DriversAdapter(private val drivers: List<Driver>) : RecyclerView.Adapter<DriversAdapter.DriverViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_driver, parent, false)
        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driver = drivers[position]
        holder.bind(driver)
    }

    override fun getItemCount() = drivers.size

    class DriverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val photoImageView: ImageView = itemView.findViewById(R.id.imageViewPhoto)

        fun bind(driver: Driver) {
            nameTextView.text = driver.name
            photoImageView.setImageResource(driver.photo)
        }
    }
}
