package com.vaskevicius.android.kittyhub.data.network

import com.vaskevicius.android.kittyhub.BuildConfig
import com.vaskevicius.android.kittyhub.data.models.ImageResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class ApiHelper @Inject
constructor(private val retrofit: Retrofit) : IApiHelper {

    override fun getPopularImageList(page: Int): Observable<ImageResponse> {
        val data: HashMap<String, String> = HashMap<String, String>()
        data.put("key", BuildConfig.API_KEY)
        data.put("q", "cat")
        data.put("per_page", 20.toString())
        data.put("page", page.toString())
        data.put("order", "popular")

        return retrofit.create(ApiService::class.java).getImageList(data)
    }

}
