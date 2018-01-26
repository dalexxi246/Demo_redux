package com.wh2soft.footy.di.components

import com.wh2soft.footy.di.scopes.ViewsScope
import com.wh2soft.footy.ui.activities.AuthActivity
import com.wh2soft.footy.ui.activities.HomeActivity
import com.wh2soft.footy.ui.activities.ReduxActivity
import com.wh2soft.footy.ui.activities.SplashActivity
import dagger.Component

@ViewsScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ActivityComponent : ApplicationComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(authActivity: AuthActivity)

    fun inject(homeActivity: HomeActivity)

    fun inject(reduxActivity: ReduxActivity)
}