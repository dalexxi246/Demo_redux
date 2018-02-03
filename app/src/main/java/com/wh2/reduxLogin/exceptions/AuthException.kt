package com.wh2.reduxLogin.exceptions

sealed class AuthException : Exception() {
    class LoginException(message: String) : AuthException()
    class RegisterException(message: String) : AuthException()
    class SessionCheckException(message: String) : AuthException()
}