package com.wh2soft.footy.di.components

import com.wh2soft.footy.di.scopes.ViewsScope
import com.wh2soft.footy.ui.fragment.LoginFragment
import com.wh2soft.footy.ui.fragment.ReduxFragment
import com.wh2soft.footy.ui.fragment.RegistryFragment
import dagger.Component

@ViewsScope
@Component(dependencies = [(ApplicationComponent::class)])
interface FragmentsComponent : ApplicationComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(registryFragment: RegistryFragment)

    fun inject(wH2Fragment: ReduxFragment)

}