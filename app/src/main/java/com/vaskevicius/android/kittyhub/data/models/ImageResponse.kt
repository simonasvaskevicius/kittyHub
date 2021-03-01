package com.vaskevicius.android.kittyhub.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageResponse(
    @Expose
    @SerializedName("total")
    val total:String,

    @Expose
    @SerializedName("totalHits")
    val totalHits:String,

    @Expose
    @SerializedName("hits")
    val images:List<Image>
    ) : Serializable