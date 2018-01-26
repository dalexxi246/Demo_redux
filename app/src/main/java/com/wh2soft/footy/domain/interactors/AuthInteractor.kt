package com.wh2soft.footy.domain.interactors

import com.wh2soft.footy.domain.model.User
import io.reactivex.Single

interface AuthInteractor {

    fun performLogin(username: String, password: String): Single<User>
    fun performRegister(user: User, password: String): Single<Boolean>
    fun checkIfSessionExists(): Single<User>

}