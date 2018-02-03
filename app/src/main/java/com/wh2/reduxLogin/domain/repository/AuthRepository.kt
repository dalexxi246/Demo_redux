package com.wh2.reduxLogin.domain.repository

import com.wh2.reduxLogin.domain.model.User
import io.reactivex.Single

interface AuthRepository {

    fun signIn(email: String, password: String): Single<User>
    fun signUp(user: User, password: String): Single<Boolean>
    fun sessionExists(): Single<User>
}