package com.vaskevicius.android.kittyhub.data.prefs

import com.vaskevicius.android.kittyhub.data.models.Image

interface Preferences {
    fun setFavorites(favoriteImages: List<Image>)
    fun getFavorites(): List<Image>
}