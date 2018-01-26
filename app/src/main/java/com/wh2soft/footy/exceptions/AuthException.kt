package com.wh2soft.footy.exceptions

sealed class AuthException : Exception() {
    class LoginException(message: String) : AuthException()
    class RegisterException(message: String) : AuthException()
    class SessionCheckException(message: String) : AuthException()
}