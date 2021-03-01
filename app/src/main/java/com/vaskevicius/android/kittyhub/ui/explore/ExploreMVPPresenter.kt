package com.vaskevicius.android.kittyhub.ui.explore

import com.vaskevicius.android.kittyhub.framework.base.mvp.BaseMvpPresenter

interface ExploreMVPPresenter<V: ExploreView> : BaseMvpPresenter<V> {

    fun loadPopularImages(page: Int)

    fun onViewPrepared()
}