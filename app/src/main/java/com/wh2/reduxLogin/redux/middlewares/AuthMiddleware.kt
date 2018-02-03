package com.wh2.reduxLogin.redux.middlewares

import com.brianegan.bansa.Action
import com.brianegan.bansa.Middleware
import com.brianegan.bansa.NextDispatcher
import com.brianegan.bansa.Store
import com.wh2.reduxLogin.domain.interactors.AuthInteractor
import com.wh2.reduxLogin.redux.actions.*
import com.wh2.reduxLogin.redux.states.ApplicationState
import com.wh2.reduxLogin.redux.states.AuthState

class AuthMiddleware(private val authInteractor: AuthInteractor) : Middleware<ApplicationState> {

    override fun dispatch(store: Store<ApplicationState>, action: Action, next: NextDispatcher) {
        when (action) {
            INIT -> {
                authInteractor.checkIfSessionExists().subscribe(
                        { user -> next.dispatch(SUCCESSFUL_AUTH(user, AuthState.AuthResult.SUCCESS_LOGIN)) },
                        { next.dispatch(CHANGE_TO_LOGIN) }
                )
            }
            is PERFORM_LOGIN_BY_EMAIL -> {
                next.dispatch(action)
                authInteractor.performLogin(action.username, action.password).subscribe(
                        { user -> next.dispatch(SUCCESSFUL_AUTH(user, AuthState.AuthResult.SUCCESS_LOGIN)) },
                        { throwable -> next.dispatch(DISPLAY_LOGIN_BY_EMAIL_ERROR(throwable.localizedMessage)) }
                )
            }
            is PERFORM_REGISTER -> {
                authInteractor.performRegister(action.user, action.password).subscribe(
                        { next.dispatch(SUCCESSFUL_AUTH(action.user, AuthState.AuthResult.SUCCESS_REGISTER)) },
                        { throwable -> next.dispatch(DISPLAY_REGISTER_ERROR(throwable.localizedMessage)) }
                )
            }
            else -> next.dispatch(action)
        }
    }
}
