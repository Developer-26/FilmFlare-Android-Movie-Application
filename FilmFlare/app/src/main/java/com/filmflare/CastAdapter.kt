package com.filmflare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CastAdapter(private val castNames: List<String>, private val castImages: List<String>) :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val castName: TextView = itemView.findViewById(R.id.tvCastName)
        val castImage: ImageView = itemView.findViewById(R.id.ivCastImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.castName.text = castNames[position]

        // Load the cast image using Glide and apply circle crop
        Glide.with(holder.itemView.context)
            .load(castImages[position])
            .circleCrop()  // Apply circle cropping to make the image circular
            .into(holder.castImage)
    }

    override fun getItemCount(): Int = castNames.size
}
