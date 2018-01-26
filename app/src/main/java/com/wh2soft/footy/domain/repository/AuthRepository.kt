package com.wh2soft.footy.domain.repository

import com.wh2soft.footy.domain.model.User
import io.reactivex.Single

interface AuthRepository {

    fun signIn(email: String, password: String): Single<User>
    fun signUp(user: User, password: String): Single<Boolean>
    fun sessionExists(): Single<User>
}