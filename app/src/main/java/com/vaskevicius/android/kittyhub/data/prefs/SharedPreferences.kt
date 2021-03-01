package com.vaskevicius.android.kittyhub.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vaskevicius.android.kittyhub.data.models.Image
import com.vaskevicius.android.kittyhub.framework.di.ApplicationContext
import com.vaskevicius.android.kittyhub.framework.di.PreferenceInfo
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPreferences @Inject
constructor(
    @ApplicationContext context: Context,
    @PreferenceInfo prefFileName: String
) : Preferences {

    companion object {
        val KEY_FAVORITE_IMAGES = "prefs.favorite"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(
        prefFileName,
        Context.MODE_PRIVATE
    )
    override fun setFavorites(favoriteImages: List<Image>) {
        val connectionsJSONString = Gson().toJson(favoriteImages)
        prefs.edit().putString(KEY_FAVORITE_IMAGES, connectionsJSONString).commit()
    }


    override fun getFavorites(): List<Image> {
        val favoritesJSONString: String? = prefs.getString(KEY_FAVORITE_IMAGES, null)
        return if(favoritesJSONString != null) {
            val type: Type = object : TypeToken<List<Image?>?>() {}.type
            Gson().fromJson(favoritesJSONString, type);
        } else mutableListOf()
    }
}