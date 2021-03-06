package com.wh2.reduxLogin.redux.reducers

import com.brianegan.bansa.Action
import com.brianegan.bansa.Reducer
import com.wh2.reduxLogin.redux.states.ApplicationState

class ApplicationReducer(private val authReducer: AuthReducer) : Reducer<ApplicationState> {

    override fun reduce(oldState: ApplicationState, action: Action) =
            with(oldState) {
                this.copy(
                        authState = authReducer.reduce(oldState.authState, action)
                )
            }

}
