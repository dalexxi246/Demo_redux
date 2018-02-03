package com.wh2.reduxLogin.domain.interactors

import com.wh2.reduxLogin.domain.model.User
import io.reactivex.Single

interface AuthInteractor {

    fun performLogin(username: String, password: String): Single<User>
    fun performRegister(user: User, password: String): Single<Boolean>
    fun checkIfSessionExists(): Single<User>

}