package com.example.claudio_garrido_20240531_continuidad_ing_informatica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PharmacyAdapter(private val pharmacies: List<Pharmacy>) : RecyclerView.Adapter<PharmacyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val comunaTextView: TextView = view.findViewById(R.id.comunaTextView)
        val direccionTextView: TextView = view.findViewById(R.id.direccionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pharmacy_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pharmacy = pharmacies[position]
        holder.nameTextView.text = pharmacy.local_nombre
        holder.comunaTextView.text = pharmacy.comuna_nombre
        holder.direccionTextView.text = pharmacy.local_direccion
    }

    override fun getItemCount() = pharmacies.size
}
