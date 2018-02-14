package com.wh2.reduxLogin.redux.actions

import com.brianegan.bansa.Action
import com.wh2.reduxLogin.domain.model.User
import com.wh2.reduxLogin.redux.states.AuthState

data class PERFORM_REGISTER(val user: User, val password: String): Action
data class PERFORM_LOGIN_BY_EMAIL(val username: String, val password: String): Action
data class SUCCESSFUL_AUTH(val user: User, val authResult: AuthState.AuthResult): Action
data class DISPLAY_REGISTER_ERROR(val message: String?): Action
data class DISPLAY_LOGIN_BY_EMAIL_ERROR(val message: String?): Action
object CHANGE_TO_LOGIN: Action
object CHANGE_TO_REGISTER: Action
object INIT: Action

fun signUp(user: User, password: String): PERFORM_REGISTER = PERFORM_REGISTER(user, password)

fun login(username: String, password: String) = PERFORM_LOGIN_BY_EMAIL(username, password)

//todo: "Implementar nuevas factory functions"