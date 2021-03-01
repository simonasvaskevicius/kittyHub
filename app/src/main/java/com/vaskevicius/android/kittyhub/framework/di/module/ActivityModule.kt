package com.vaskevicius.android.kittyhub.framework.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.vaskevicius.android.kittyhub.data.prefs.Preferences
import com.vaskevicius.android.kittyhub.data.prefs.SharedPreferences
import com.vaskevicius.android.kittyhub.framework.di.ActivityContext
import com.vaskevicius.android.kittyhub.ui.explore.ExploreMVPPresenter
import com.vaskevicius.android.kittyhub.ui.explore.ExplorePresenter
import com.vaskevicius.android.kittyhub.ui.explore.ExploreView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    fun provideExplorePresenter(explorePresenter: ExplorePresenter<ExploreView>): ExploreMVPPresenter<ExploreView> = explorePresenter

}