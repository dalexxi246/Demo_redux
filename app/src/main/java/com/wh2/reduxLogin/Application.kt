package com.wh2.reduxLogin

import android.app.Application
import android.content.Context
import com.wh2.reduxLogin.di.components.ApplicationComponent
import com.wh2.reduxLogin.di.components.DaggerApplicationComponent
import com.wh2.reduxLogin.di.modules.PersistanceModule
import com.wh2.reduxLogin.di.modules.ReduxModule

class Application : Application() {

    companion object {
        @JvmStatic lateinit var applicationComponent: ApplicationComponent
        lateinit var app: com.wh2.reduxLogin.Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initDagger()
    }

    private fun initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .reduxModule(ReduxModule())
                .persistanceModule(PersistanceModule(getContext()))
                .build()
    }

    fun getContext(): Context {
        return applicationContext
    }
}
