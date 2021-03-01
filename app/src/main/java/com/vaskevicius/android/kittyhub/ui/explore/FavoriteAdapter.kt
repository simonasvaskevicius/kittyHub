package com.vaskevicius.android.kittyhub.ui.explore

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.makeramen.roundedimageview.RoundedImageView
import com.vaskevicius.android.kittyhub.R
import com.vaskevicius.android.kittyhub.data.models.Image
import com.vaskevicius.android.kittyhub.framework.base.DoubleClickListener


class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var onImageSingleClick: ((Image) -> Unit)? = null
    var onImageDoubleClick: ((Image) -> Unit)? = null
    private var favoriteImages = listOf<Image>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        context = parent?.context
        return FavoriteViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.li_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val image = favoriteImages[position]

        Glide.with(context).load(image.previewURL).crossFade()
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image)
    }

    override fun getItemCount(): Int {
        return favoriteImages.size
    }

    fun setImageList(favoriteImages: List<Image>) {
        this.favoriteImages = favoriteImages
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: RoundedImageView = itemView.findViewById(R.id.image)
        var favorite: ImageView = itemView.findViewById(R.id.favorite)

        init {
            itemView.setOnClickListener(object : DoubleClickListener() {
                override fun onSingleClick(v: View?) {
                    onImageSingleClick?.invoke(favoriteImages[adapterPosition])
                }

                override fun onDoubleClick(v: View?) {
                    onImageDoubleClick?.invoke(favoriteImages[adapterPosition])
                    favorite.visibility = View.VISIBLE
                    Handler().postDelayed({
                        favorite.visibility = View.GONE
                    }, 1000)
                }

            })
        }
    }

}
