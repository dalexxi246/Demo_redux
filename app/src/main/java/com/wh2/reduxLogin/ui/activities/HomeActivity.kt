package com.wh2.reduxLogin.ui.activities

import android.os.Bundle
import com.wh2.reduxLogin.R

class HomeActivity : ReduxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}