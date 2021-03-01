package com.vaskevicius.android.kittyhub.framework.base.mvp

abstract class BasePresenter<V : BaseMvpView> : BaseMvpPresenter<V> {

    protected var view: V? = null

    protected val isViewAttached: Boolean
        get() = view != null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }
}