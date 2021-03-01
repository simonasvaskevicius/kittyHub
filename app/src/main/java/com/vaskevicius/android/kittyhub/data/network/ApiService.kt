package com.vaskevicius.android.kittyhub.data.network

import com.vaskevicius.android.kittyhub.data.models.ImageResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("api/")
    fun getImageList(@QueryMap options: Map<String, String>): Observable<ImageResponse>

}