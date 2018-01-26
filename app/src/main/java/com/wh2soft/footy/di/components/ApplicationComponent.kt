package com.wh2soft.footy.di.components

import com.brianegan.bansa.Store
import com.wh2soft.footy.di.modules.DomainModule
import com.wh2soft.footy.di.modules.ReduxModule
import com.wh2soft.footy.di.modules.ViewModelsFactoriesModule
import com.wh2soft.footy.redux.states.ApplicationState
import com.wh2soft.footy.ui.viewmodels.factories.AuthViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ReduxModule::class, DomainModule::class, ViewModelsFactoriesModule::class))
interface ApplicationComponent {

    fun Store() : Store<ApplicationState>

    fun AuthViewModelFactory() : AuthViewModelFactory

}