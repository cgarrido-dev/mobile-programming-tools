package com.example.claudio_garrido_20240608_continuidad_ing_informatica

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.Button

class ImagenAdapter(private val context: Context, private val listaImagenes: List<Imagen>) :
    RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagenViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_imagen, parent, false)
        return ImagenViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImagenViewHolder, position: Int) {
        val imagenActual = listaImagenes[position]
        holder.tituloTextView.text = imagenActual.titulo
        Glide.with(holder.itemView.context).load(imagenActual.url).into(holder.imagenImageView)

        holder.itemView.setOnClickListener {
            mostrarDialog(imagenActual.url)
        }
    }

    override fun getItemCount() = listaImagenes.size

    private fun mostrarDialog(url: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_imagen)
        val dialogImageView = dialog.findViewById<ImageView>(R.id.dialogImageView)
        val closeButton = dialog.findViewById<Button>(R.id.closeButton)

        Glide.with(context).load(url).into(dialogImageView)

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    class ImagenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imagenImageView)
    }
}
