package com.wh2soft.footy.di.modules

import com.brianegan.bansa.BaseStore
import com.brianegan.bansa.Reducer
import com.brianegan.bansa.Store
import com.wh2soft.footy.domain.interactors.AuthInteractor
import com.wh2soft.footy.redux.middlewares.AuthMiddleware
import com.wh2soft.footy.redux.reducers.ApplicationReducer
import com.wh2soft.footy.redux.reducers.AuthReducer
import com.wh2soft.footy.redux.states.ApplicationState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReduxModule {

    @Provides @Singleton
    fun store(initialState: ApplicationState, reducer: Reducer<ApplicationState>, authMiddleware: AuthMiddleware): Store<ApplicationState> =
            BaseStore<ApplicationState>(initialState, reducer, authMiddleware)

    @Provides @Singleton
    fun initialApplicationState() : ApplicationState = ApplicationState()

    @Provides @Singleton
    fun applicationReducer(authReducer: AuthReducer): Reducer<ApplicationState> = ApplicationReducer(authReducer)

    @Provides @Singleton
    fun authReducer() : AuthReducer = AuthReducer()

    @Provides @Singleton
    fun authMiddleware(authInteractor: AuthInteractor) = AuthMiddleware(authInteractor)

}
