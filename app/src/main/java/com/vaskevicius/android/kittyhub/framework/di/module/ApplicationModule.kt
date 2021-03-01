package com.vaskevicius.android.kittyhub.framework.di.module

import android.app.Application
import android.content.Context
import com.vaskevicius.android.kittyhub.BuildConfig
import com.vaskevicius.android.kittyhub.data.network.ApiHelper
import com.vaskevicius.android.kittyhub.data.prefs.Preferences
import com.vaskevicius.android.kittyhub.data.prefs.SharedPreferences
import com.vaskevicius.android.kittyhub.framework.di.ApplicationContext
import com.vaskevicius.android.kittyhub.framework.di.PreferenceInfo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(): Preferences {
        return SharedPreferences(application, BuildConfig.PREF_NAME)
    }
    @Provides
    @Singleton
    fun provideApiHelper(retrofit: Retrofit): ApiHelper {
        return ApiHelper(retrofit)
    }


}