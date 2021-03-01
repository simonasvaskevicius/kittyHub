package com.vaskevicius.android.kittyhub.data.network

import com.vaskevicius.android.kittyhub.data.models.ImageResponse
import io.reactivex.Observable

interface IApiHelper {

    fun getPopularImageList(page:Int): Observable<ImageResponse>

}