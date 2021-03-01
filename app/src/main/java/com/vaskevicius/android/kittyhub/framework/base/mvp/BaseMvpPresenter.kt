package com.vaskevicius.android.kittyhub.framework.base.mvp

interface BaseMvpPresenter<V : BaseMvpView> {
    fun onAttach(view: V)
    fun onDetach()
}