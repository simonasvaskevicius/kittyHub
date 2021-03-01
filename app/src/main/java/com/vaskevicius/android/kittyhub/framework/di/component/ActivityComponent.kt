package com.vaskevicius.android.kittyhub.framework.di.component

import com.vaskevicius.android.kittyhub.framework.di.PerActivity
import com.vaskevicius.android.kittyhub.framework.di.module.ActivityModule
import com.vaskevicius.android.kittyhub.ui.explore.ExploreActivity
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(exploreActivity: ExploreActivity)

}