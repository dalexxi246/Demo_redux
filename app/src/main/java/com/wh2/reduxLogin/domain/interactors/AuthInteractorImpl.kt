package com.wh2.reduxLogin.domain.interactors

import com.wh2.reduxLogin.domain.model.User
import com.wh2.reduxLogin.domain.repository.AuthRepository
import io.reactivex.Single

class AuthInteractorImpl(private val authRepository: AuthRepository) : AuthInteractor {

    override fun checkIfSessionExists(): Single<User> {
        return authRepository.sessionExists()
    }

    override fun performLogin(username: String, password: String): Single<User> {
        return authRepository.signIn(username, password)
    }

    override fun performRegister(user: User, password: String): Single<Boolean> {
        return authRepository.signUp(user, password)
    }
}