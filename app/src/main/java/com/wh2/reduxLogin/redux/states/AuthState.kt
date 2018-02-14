package com.wh2.reduxLogin.redux.states

import com.wh2.reduxLogin.domain.model.User

data class AuthState internal constructor(
        val loading: Boolean = false,
        val authResult: AuthResult = AuthResult.IDLE,
        val authStage: AuthStage = AuthStage.SIGN_IN,
        val errorMessage: String? = "",
        val user: User = User()
) : BaseUIState(loading, errorMessage) {

    enum class AuthResult {
        IDLE, SUCCESS_LOGIN, SUCCESS_REGISTER, LOGIN_FAILED, PASSWORD_UNMATCH, REGISTER_FAILED
    }

    enum class AuthStage {
        SIGN_IN, SIGN_UP, GOING_TO_HOME
    }
}
