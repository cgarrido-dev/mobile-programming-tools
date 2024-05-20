package com.example.claudio_garrido_20240515_continuidad_ing_informatica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.io.File

class PhotoAdapter(private val context: Context, private val photos: MutableList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return photos.size
    }

    override fun getItem(position: Int): Any {
        return photos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val photoPath = photos[position]
        Glide.with(context).load(photoPath).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val intent = ImageViewActivity.newIntent(context, photoPath)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            val file = File(photoPath)
            if (file.exists()) {
                file.delete()
                photos.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context, "Foto eliminada", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private class ViewHolder(view: View) {
        val imageView: ImageView = view.findViewById(R.id.imageViewPhoto)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)
    }
}
