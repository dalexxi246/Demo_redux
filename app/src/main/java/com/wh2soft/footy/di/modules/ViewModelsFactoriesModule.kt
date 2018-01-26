package com.wh2soft.footy.di.modules

import com.brianegan.bansa.Store
import com.wh2soft.footy.redux.states.ApplicationState
import com.wh2soft.footy.ui.viewmodels.factories.AuthViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelsFactoriesModule {

    @Provides @Singleton
    fun authViewModelFactory(store: Store<ApplicationState>): AuthViewModelFactory = AuthViewModelFactory(store)

}