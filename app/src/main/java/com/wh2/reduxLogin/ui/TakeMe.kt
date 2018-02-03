package com.wh2.reduxLogin.ui

import android.content.Context
import android.content.Intent
import com.wh2.reduxLogin.ui.activities.AuthActivity
import com.wh2.reduxLogin.ui.activities.HomeActivity

class TakeMe(private val context: Context) {

    companion object {

        fun from(context: Context): TakeMe {
            return TakeMe(context)
        }

    }

    fun toSignIn() {
        val starter = Intent(context, AuthActivity::class.java)
        starter.putExtra(AuthActivity.EXTRA_CURRENT_FRAGMENT, AuthActivity.SIGN_IN)
        context.startActivity(starter)
    }

    fun toSignUp() {
        val starter = Intent(context, AuthActivity::class.java)
        starter.putExtra(AuthActivity.EXTRA_CURRENT_FRAGMENT, AuthActivity.SIGN_UP)
        context.startActivity(starter)
    }

    fun toHome() {
        val starter = Intent(context, HomeActivity::class.java)
        context.startActivity(starter)
    }

}