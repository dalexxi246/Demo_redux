package com.wh2.reduxLogin.di.modules

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.wh2.reduxLogin.domain.interactors.AuthInteractor
import com.wh2.reduxLogin.domain.interactors.AuthInteractorImpl
import com.wh2.reduxLogin.domain.repository.AuthRepository
import com.wh2.reduxLogin.domain.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides @Singleton
    fun authInteractor(authRepository: AuthRepository) : AuthInteractor = AuthInteractorImpl(authRepository)

    @Provides @Singleton
    fun authRepository(rxSharedPreferences: RxSharedPreferences) : AuthRepository = AuthRepositoryImpl(rxSharedPreferences)
}