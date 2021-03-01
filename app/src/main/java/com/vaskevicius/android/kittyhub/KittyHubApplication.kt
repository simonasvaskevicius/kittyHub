package com.vaskevicius.android.kittyhub

import android.app.Application
import com.vaskevicius.android.kittyhub.framework.di.component.ApplicationComponent
import com.vaskevicius.android.kittyhub.framework.di.component.DaggerApplicationComponent
import com.vaskevicius.android.kittyhub.framework.di.module.ApplicationModule
import com.vaskevicius.android.kittyhub.framework.di.module.NetworkModule

class KittyHubApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule())
            .build()

        component.inject(this)

        instance = this
    }
}