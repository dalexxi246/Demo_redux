package com.wh2soft.footy.domain.repository

import com.wh2soft.footy.domain.model.User
import com.wh2soft.footy.exceptions.AuthException
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl : AuthRepository {

    private val EMAIL_REGISTERED = "wilmer@mail.com"
    private val PASSWORD_REGISTERED = "12345"

    override fun signIn(email: String, password: String): Single<User> = Single.create {
        e: SingleEmitter<User> ->
        run {
            if (email == EMAIL_REGISTERED && password == PASSWORD_REGISTERED) {
                e.onSuccess(User(
                        email = email,
                        password = password,
                        realName = "Wilmer Hurtado",
                        username = "@wilmerhh",
                        uuid = "kldsjajklsdalk",
                        birthdate = "1991/07/06"
                ))
            } else {
                e.onError(AuthException.LoginException("Unauthorized"))
            }
        }
    }.delay(3, TimeUnit.SECONDS)

    override fun signUp(user: User, password: String): Single<Boolean> = Single.create {
        emitter: SingleEmitter<Boolean> ->
        run {
            if (user.email == EMAIL_REGISTERED ) {
                emitter.onError(AuthException.RegisterException("Email exists"))
            } else {
                emitter.onSuccess(true)
            }
        }
    }.delay(3, TimeUnit.SECONDS)

    override fun sessionExists(): Single<User> = Single.create {
        if (Math.random().dec() < 10) {
            it.onSuccess(User(
                    email = EMAIL_REGISTERED,
                    password = PASSWORD_REGISTERED,
                    realName = "Wilmer Hurtado",
                    username = "@wilmerhh",
                    uuid = "kldsjajklsdalk",
                    birthdate = "1991/07/06"
            ))
        } else {
            it.onError(AuthException.SessionCheckException("Need authenticate"))
        }
    }

}