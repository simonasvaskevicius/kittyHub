package com.vaskevicius.android.kittyhub.ui.explore

import android.annotation.SuppressLint
import android.util.Log
import com.vaskevicius.android.kittyhub.data.models.ImageResponse
import com.vaskevicius.android.kittyhub.data.network.ApiHelper
import com.vaskevicius.android.kittyhub.framework.base.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExplorePresenter<V : ExploreView> @Inject constructor(var apiHelper: ApiHelper) :
    BasePresenter<V>(), ExploreMVPPresenter<V> {


    override fun onViewPrepared() {
        loadPopularImages(1)
    }

    @SuppressLint("CheckResult")
    override fun loadPopularImages(page: Int) {
        view?.showLoading()
        apiHelper.getPopularImageList(page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageResponse>() {
                override fun onNext(t: ImageResponse) {
                    //totalPages= total items/ items per page
                   view?.refreshItemList(t.images, page, t.totalHits.toInt()/20)
                }

                override fun onError(e: Throwable) {
                    Log.e("Network error", e.toString())
                }

                override fun onComplete() {
                    view?.hideLoading()
                }

            })
    }
}