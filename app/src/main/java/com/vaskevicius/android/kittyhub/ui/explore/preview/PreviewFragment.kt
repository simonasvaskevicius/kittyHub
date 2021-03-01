package com.vaskevicius.android.kittyhub.ui.explore.preview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.vaskevicius.android.kittyhub.R
import com.vaskevicius.android.kittyhub.data.models.Image
import kotlinx.android.synthetic.main.fragment_preview.*


class PreviewFragment constructor(var image: Image) : Fragment() {

    companion object {
        fun newInstance(image: Image): PreviewFragment {
            return PreviewFragment(image)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImage()
        favorites.text = image.favorites.toString()
        views.text = image.views.toString()
        downloads.text = image.downloads.toString()
        likes.text = image.likes.toString()
        user.text = image.user
        back.setOnClickListener { activity?.onBackPressed()}
    }

    private fun initImage() {
        Glide.with(context).load(image.largeImageURL)
            .asBitmap().animate(R.anim.enter)
            .into(object : SimpleTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                    progressBar.visibility = View.GONE
                    shareLayout.setOnClickListener { share(resource) }

                    //Getting dominant image color and setting it to background
                    Palette.Builder(resource!!).generate {
                        it?.let { palette ->
                            val dominantColor = palette.getDominantColor(
                                ContextCompat.getColor(
                                    context!!,
                                    R.color.default_background
                                )
                            )
                            container.setBackgroundColor(dominantColor)
                        }
                    }
                    imageView.setImageBitmap(resource)
                }
            })
    }

    private fun share(bitmap: Bitmap?) {
        val bitmapPath: String = MediaStore.Images.Media.insertImage(activity?.contentResolver, bitmap, "title", null)
        val bitmapUri = Uri.parse(bitmapPath)

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        shareIntent.type = "image/*"
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }
}