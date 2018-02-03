package com.wh2.reduxLogin.redux.states

import com.wh2.reduxLogin.domain.model.User

data class ApplicationState (
        val authState: AuthState = AuthState(),
        val userLogged: User = User()
)
