package com.nationalconclave.bseb.ui.main.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nationalconclave.bseb.R
import com.nationalconclave.bseb.ui.main.model.Movie


class ContentItemViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val title: TextView = root.findViewById(R.id.text_view)
    val image: ImageView = root.findViewById(R.id.image_view)

    fun bind(response: Movie?) {
        title.text = response?.title?:"Default Movie"
        Glide.with(root.context).load(response?.poster?:"")
            .placeholder(R.drawable.app_icon).into(image)

    }


}