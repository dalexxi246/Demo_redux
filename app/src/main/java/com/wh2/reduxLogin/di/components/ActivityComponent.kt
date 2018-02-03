package com.wh2.reduxLogin.di.components

import com.wh2.reduxLogin.di.scopes.ViewsScope
import com.wh2.reduxLogin.ui.activities.AuthActivity
import com.wh2.reduxLogin.ui.activities.HomeActivity
import com.wh2.reduxLogin.ui.activities.ReduxActivity
import com.wh2.reduxLogin.ui.activities.SplashActivity
import dagger.Component

@ViewsScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ActivityComponent : ApplicationComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(authActivity: AuthActivity)

    fun inject(homeActivity: HomeActivity)

    fun inject(reduxActivity: ReduxActivity)
}