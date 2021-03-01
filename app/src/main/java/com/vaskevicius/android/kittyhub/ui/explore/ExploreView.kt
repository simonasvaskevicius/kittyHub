package com.vaskevicius.android.kittyhub.ui.explore

import com.vaskevicius.android.kittyhub.data.models.Image
import com.vaskevicius.android.kittyhub.framework.base.mvp.BaseMvpView

interface ExploreView : BaseMvpView {
    fun refreshItemList(images: List<Image>, page: Int, totalPages: Int)
}