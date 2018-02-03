package com.wh2.reduxLogin.di.components

import com.wh2.reduxLogin.di.scopes.ViewsScope
import com.wh2.reduxLogin.ui.fragment.LoginFragment
import com.wh2.reduxLogin.ui.fragment.ReduxFragment
import com.wh2.reduxLogin.ui.fragment.RegistryFragment
import dagger.Component

@ViewsScope
@Component(dependencies = [(ApplicationComponent::class)])
interface FragmentsComponent : ApplicationComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(registryFragment: RegistryFragment)

    fun inject(wH2Fragment: ReduxFragment)

}