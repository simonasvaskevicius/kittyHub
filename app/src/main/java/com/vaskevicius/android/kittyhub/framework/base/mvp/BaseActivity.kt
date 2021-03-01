package com.vaskevicius.android.kittyhub.framework.base.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vaskevicius.android.kittyhub.KittyHubApplication
import com.vaskevicius.android.kittyhub.framework.di.component.ActivityComponent
import com.vaskevicius.android.kittyhub.framework.di.component.DaggerActivityComponent
import com.vaskevicius.android.kittyhub.framework.di.module.ActivityModule

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent((application as KittyHubApplication).component)
            .build()
        injectComponent(activityComponent)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectComponent(component: ActivityComponent)

}