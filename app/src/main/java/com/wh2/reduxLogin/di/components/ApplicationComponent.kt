package com.wh2.reduxLogin.di.components

import android.content.Context
import com.brianegan.bansa.Store
import com.wh2.reduxLogin.di.modules.DomainModule
import com.wh2.reduxLogin.di.modules.PersistanceModule
import com.wh2.reduxLogin.di.modules.ReduxModule
import com.wh2.reduxLogin.di.modules.ViewModelsFactoriesModule
import com.wh2.reduxLogin.redux.states.ApplicationState
import com.wh2.reduxLogin.ui.viewmodels.factories.AuthViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ReduxModule::class, DomainModule::class, ViewModelsFactoriesModule::class, PersistanceModule::class))
interface ApplicationComponent {

    fun Store() : Store<ApplicationState>

    fun AuthViewModelFactory() : AuthViewModelFactory

}