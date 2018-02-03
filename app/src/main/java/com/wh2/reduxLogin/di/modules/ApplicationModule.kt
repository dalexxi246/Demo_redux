package com.wh2.reduxLogin.di.modules

import android.content.Context
import com.wh2.reduxLogin.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Provides @Singleton
    fun context() : Context = application.applicationContext
}