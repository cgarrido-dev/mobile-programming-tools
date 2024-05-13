package com.example.claudio_garrido_20240507_continuidad_ing_informatica

import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.widget.BaseAdapter

class ToyAdapter(
    private val context: Context,
    private val toyList: MutableList<Toy>
) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private class ViewHolder(row: View) {
        val toyImageView: ImageView = row.findViewById(R.id.toyImageView)
        val toyNameTextView: TextView = row.findViewById(R.id.toyNameTextView)
        val toyPriceTextView: TextView = row.findViewById(R.id.toyPriceTextView)
    }

    override fun getCount(): Int = toyList.size
    override fun getItem(position: Int): Toy = toyList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_toy, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val toy = getItem(position)
        viewHolder.toyImageView.setImageResource(toy.image)
        viewHolder.toyNameTextView.text = toy.name
        viewHolder.toyPriceTextView.text = "$${toy.price}"

        return view
    }

    fun removeItem(position: Int) {
        toyList.removeAt(position)
        notifyDataSetChanged()
    }
}