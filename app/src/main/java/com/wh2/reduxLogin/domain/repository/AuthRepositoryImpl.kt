package com.wh2.reduxLogin.domain.repository

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.wh2.reduxLogin.domain.model.User
import com.wh2.reduxLogin.exceptions.AuthException
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(val preferencesManager: RxSharedPreferences) : AuthRepository {

    private val EMAIL_REGISTERED = "wilmer@mail.com"
    private val PASSWORD_REGISTERED = "12345"

    private var userExists = false

    override fun signIn(email: String, password: String): Single<User> = Single.create {
        e: SingleEmitter<User> ->
        run {
            if (email == EMAIL_REGISTERED && password == PASSWORD_REGISTERED) {
                preferencesManager.getBoolean(PreferencesKeys.USER_EXISTS.key).set(true)
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
                preferencesManager.getBoolean(PreferencesKeys.USER_EXISTS.key).set(true)
                emitter.onSuccess(true)
            }
        }
    }.delay(3, TimeUnit.SECONDS)

    override fun sessionExists(): Single<User> = Single.create {
        userExists = preferencesManager.getBoolean(PreferencesKeys.USER_EXISTS.key, PreferencesKeys.USER_EXISTS.defaultValue as Boolean).get()
        if (userExists) {
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

enum class PreferencesKeys(val key : String, val defaultValue : Any) {
    USER_EXISTS("user_exists", false)
}