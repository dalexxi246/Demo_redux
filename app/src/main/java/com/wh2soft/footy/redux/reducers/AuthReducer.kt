package com.wh2soft.footy.redux.reducers

import com.brianegan.bansa.Action
import com.brianegan.bansa.Reducer
import com.wh2soft.footy.redux.actions.*
import com.wh2soft.footy.redux.states.AuthState

class AuthReducer : Reducer<AuthState> {

    override fun reduce(state: AuthState, action: Action): AuthState {

        when (action) {

            is PERFORM_LOGIN_BY_EMAIL,
            is PERFORM_REGISTER ->
                return state.copy(loading = true, authResult = AuthState.AuthResult.IDLE)

            is SUCCESSFUL_AUTH ->
                return state.copy(loading = false, authResult = action.authResult, authStage = AuthState.AuthStage.GOING_TO_HOME, user = action.user)

            is DISPLAY_LOGIN_BY_EMAIL_ERROR ->
                return state.copy(loading = false, errorMessage = action.message, authResult = AuthState.AuthResult.LOGIN_FAILED)

            is DISPLAY_REGISTER_ERROR ->
                return state.copy(loading = false, errorMessage = action.message, authResult = AuthState.AuthResult.REGISTER_FAILED)

            is CHANGE_TO_LOGIN ->
                return state.copy(loading = false, authStage = AuthState.AuthStage.SIGN_IN)

            is CHANGE_TO_REGISTER ->
                return state.copy(loading = false, authStage = AuthState.AuthStage.SIGN_UP)

            else -> return state

        }
    }

}
