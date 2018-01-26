package com.wh2soft.footy

import android.app.Application
import android.content.Context
import com.wh2soft.footy.di.components.ApplicationComponent
import com.wh2soft.footy.di.components.DaggerApplicationComponent
import com.wh2soft.footy.di.modules.ReduxModule

class Application : Application() {

    companion object {
        @JvmStatic lateinit var applicationComponent: ApplicationComponent
        lateinit var app: com.wh2soft.footy.Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initDagger()
    }

    private fun initDagger() {
        applicationComponent = DaggerApplicationComponent.builder().reduxModule(ReduxModule()).build()
    }

    fun getContext(): Context {
        return applicationContext
    }
}
