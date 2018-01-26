package com.wh2soft.footy.redux.states

import com.wh2soft.footy.domain.model.User

data class ApplicationState (
        val authState: AuthState = AuthState(),
        val userLogged: User = User()
)
