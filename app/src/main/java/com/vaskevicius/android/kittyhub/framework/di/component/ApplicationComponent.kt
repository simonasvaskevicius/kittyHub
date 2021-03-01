package com.vaskevicius.android.kittyhub.framework.di.component

import android.app.Application
import android.content.Context
import com.vaskevicius.android.kittyhub.KittyHubApplication
import com.vaskevicius.android.kittyhub.data.prefs.Preferences
import com.vaskevicius.android.kittyhub.data.prefs.SharedPreferences
import com.vaskevicius.android.kittyhub.framework.di.ApplicationContext
import com.vaskevicius.android.kittyhub.framework.di.module.ApplicationModule
import com.vaskevicius.android.kittyhub.framework.di.module.NetworkModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun inject(app: KittyHubApplication)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun retrofit(): Retrofit

    fun sharedPreferences(): Preferences

}
