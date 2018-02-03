package com.wh2.reduxLogin.ui.activities

import android.os.Bundle
import com.brianegan.bansa.Store
import com.wh2.reduxLogin.R
import com.wh2.reduxLogin.redux.actions.INIT
import com.wh2.reduxLogin.redux.states.ApplicationState
import com.wh2.reduxLogin.redux.states.AuthState
import com.wh2.reduxLogin.ui.TakeMe
import javax.inject.Inject

class SplashActivity : ReduxActivity() {

    @Inject
    lateinit var store: Store<ApplicationState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        store.subscribe { state -> render(state) }
        store.dispatch(INIT)
    }

    override fun inject() {
        activityComponent.inject(this)
    }

    private fun render(applicationState: ApplicationState) {
        applicationState.authState.authStage.let { goTo(it) }
    }

    private fun goTo(authStage: AuthState.AuthStage?) {
        when (authStage) {
            AuthState.AuthStage.SIGN_IN -> TakeMe.from(this).toSignIn()
            AuthState.AuthStage.SIGN_UP -> TakeMe.from(this).toSignUp()
            AuthState.AuthStage.GOING_TO_HOME -> TakeMe.from(this).toHome()
        }
        finish()
    }

}
