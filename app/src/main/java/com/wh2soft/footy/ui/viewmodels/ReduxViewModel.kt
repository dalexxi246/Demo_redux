package com.wh2soft.footy.ui.viewmodels

import android.arch.lifecycle.ViewModel
import com.brianegan.bansa.Store
import com.wh2soft.footy.redux.states.ApplicationState

abstract class ReduxViewModel(protected val store: Store<ApplicationState>) : ViewModel() {

    init {
        this.store.subscribe { state -> render(state) }
    }

    protected open fun render(state: ApplicationState) {

    }

}
