package com.wh2soft.footy.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.evernote.android.state.State
import com.wh2soft.footy.R
import com.wh2soft.footy.databinding.ActivityAuthBinding
import com.wh2soft.footy.redux.states.AuthState
import com.wh2soft.footy.ui.TakeMe
import com.wh2soft.footy.ui.fragment.LoginFragment
import com.wh2soft.footy.ui.fragment.RegistryFragment
import com.wh2soft.footy.ui.viewmodels.AuthViewModel
import com.wh2soft.footy.ui.viewmodels.factories.AuthViewModelFactory
import javax.inject.Inject

class AuthActivity : ReduxActivity() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    companion object {
        const val EXTRA_CURRENT_FRAGMENT = "show_first"
        const val SIGN_IN = 1
        const val SIGN_UP = 2
        const val LOGIN_FRAGMENT = "LOGIN_FRAGMENT"
        const val REGISTRY_FRAGMENT = "REGISTRY_FRAGMENT"
    }

    private lateinit var activityAuthBinding: ActivityAuthBinding

    private var loginFragment: Fragment? = null
    private var registryFragment: Fragment? = null

    @State var currentFragmentShown = 0

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        activityAuthBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        authViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel::class.java)

        loginFragment = supportFragmentManager.findFragmentByTag(LOGIN_FRAGMENT)
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance()
        }

        registryFragment = supportFragmentManager.findFragmentByTag(REGISTRY_FRAGMENT)
        if (registryFragment == null) {
            registryFragment = RegistryFragment.newInstance()
        }

        if (currentFragmentShown == 0) {
            currentFragmentShown = intent.extras?.getInt(EXTRA_CURRENT_FRAGMENT)!!
        }

        when (currentFragmentShown) {
            SIGN_IN -> toSignIn()
            SIGN_UP -> toSignUp()
            else -> throw IllegalArgumentException("Should have an extra int for \"EXTRA_CURRENT_FRAGMENT\"")
        }

        authViewModel.stateSubject.observe(this, Observer { render(it) })
    }

    private fun render(authState: AuthState?) {
        authState?.let {
            activityAuthBinding.loadingView.visibility = if (it.loading) View.VISIBLE else View.GONE
            goTo(it.authStage)
        }
    }

    private fun goTo(authStage: AuthState.AuthStage?) {
        when (authStage) {
            AuthState.AuthStage.SIGN_IN -> toSignIn()
            AuthState.AuthStage.SIGN_UP -> toSignUp()
            AuthState.AuthStage.GOING_TO_HOME -> {
                finish()
                TakeMe.from(this).toHome()
            }
        }
    }

    private fun toSignUp() {
        supportFragmentManager
                .beginTransaction()
                .replace(activityAuthBinding.container.id, registryFragment, REGISTRY_FRAGMENT)
                .commit()
        currentFragmentShown = SIGN_UP
    }

    private fun toSignIn() {
        supportFragmentManager
                .beginTransaction()
                .replace(activityAuthBinding.container.id, loginFragment, LOGIN_FRAGMENT)
                .commit()
        currentFragmentShown = SIGN_IN
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}