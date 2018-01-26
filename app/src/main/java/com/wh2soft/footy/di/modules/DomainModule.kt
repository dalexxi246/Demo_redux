package com.wh2soft.footy.di.modules

import com.wh2soft.footy.domain.interactors.AuthInteractor
import com.wh2soft.footy.domain.interactors.AuthInteractorImpl
import com.wh2soft.footy.domain.repository.AuthRepository
import com.wh2soft.footy.domain.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides @Singleton
    fun authInteractor(authRepository: AuthRepository) : AuthInteractor = AuthInteractorImpl(authRepository)

    @Provides @Singleton
    fun authRepository() : AuthRepository = AuthRepositoryImpl()
}