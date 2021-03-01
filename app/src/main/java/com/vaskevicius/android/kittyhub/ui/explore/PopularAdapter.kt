package com.vaskevicius.android.kittyhub.ui.explore

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.makeramen.roundedimageview.RoundedImageView
import com.vaskevicius.android.kittyhub.R
import com.vaskevicius.android.kittyhub.data.models.Image
import com.vaskevicius.android.kittyhub.framework.base.DoubleClickListener

class PopularAdapter(var popularImages: MutableList<Image>) :
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    var onImageSingleClick: ((Image) -> Unit)? = null
    var onImageDoubleClick: ((Image) -> Unit)? = null
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        context = parent?.context
        return PopularViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.li_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (popularImages.isNotEmpty()) {
            val image = popularImages[position]

            Glide.with(context).load(image.previewURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image)
            holder.favorites.text = image.favorites.toString()
        }
    }

    override fun getItemCount(): Int {
        return if (popularImages.isNotEmpty()) popularImages.size else 1
    }

    inner class PopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: RoundedImageView = itemView.findViewById(R.id.image)
        var favorite: ImageView = itemView.findViewById(R.id.favorite)
        var favorites: TextView = itemView.findViewById(R.id.favorites)

        init {
            itemView.setOnClickListener(object : DoubleClickListener() {
                override fun onSingleClick(v: View?) {
                    onImageSingleClick?.invoke(popularImages[adapterPosition])
                }

                override fun onDoubleClick(v: View?) {
                    onImageDoubleClick?.invoke(popularImages[adapterPosition])
                    favorite.visibility = View.VISIBLE
                    Handler().postDelayed({
                        favorite.visibility = View.GONE
                    }, 1000)
                }

            })
        }
    }

}
