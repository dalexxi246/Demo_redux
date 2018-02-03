package com.wh2.reduxLogin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.evernote.android.state.StateSaver
import com.wh2.reduxLogin.Application
import com.wh2.reduxLogin.di.components.ActivityComponent
import com.wh2.reduxLogin.di.components.DaggerActivityComponent

abstract class ReduxActivity : AppCompatActivity() {

    companion object {
        @JvmStatic lateinit var activityComponent: ActivityComponent
    }

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().applicationComponent(Application.applicationComponent).build()
        activityComponent.inject(this)
        inject()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let { StateSaver.saveInstanceState(this, it) }
    }
}
