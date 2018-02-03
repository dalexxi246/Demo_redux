package com.wh2.reduxLogin.di.modules

import com.brianegan.bansa.BaseStore
import com.brianegan.bansa.Reducer
import com.brianegan.bansa.Store
import com.wh2.reduxLogin.domain.interactors.AuthInteractor
import com.wh2.reduxLogin.redux.middlewares.AuthMiddleware
import com.wh2.reduxLogin.redux.reducers.ApplicationReducer
import com.wh2.reduxLogin.redux.reducers.AuthReducer
import com.wh2.reduxLogin.redux.states.ApplicationState
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
    fun authMiddleware(authInteractor: AuthInteractor): AuthMiddleware = AuthMiddleware(authInteractor)

}
