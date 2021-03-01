package com.vaskevicius.android.kittyhub.data.models


data class Image(
        val id: Int,
        val pageURL: String,
        val type: String,
        val tags: String,
        val previewURL: String,
        val previewWidth: Int,
        val previewHeight: Int,
        val webformatURL: String,
        val webformatWidth: Int,
        val webformatHeight: Int,
        val largeImageURL:String,
        val imageWidth:Int,
        val imageHeight:Int,
        val views: Int,
        val downloads: Int,
        val favorites: Int,
        val likes: Int,
        val user: String
)