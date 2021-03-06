package com.wh2.reduxLogin.ui.viewmodels.factories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.brianegan.bansa.Store
import com.wh2.reduxLogin.redux.states.ApplicationState
import com.wh2.reduxLogin.ui.viewmodels.AuthViewModel

class AuthViewModelFactory(private val store: Store<ApplicationState>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(store) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
