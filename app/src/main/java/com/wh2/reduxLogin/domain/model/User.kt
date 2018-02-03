package com.wh2.reduxLogin.domain.model

data class User(
        var email: String = "",
        var uuid: String = "",
        var birthdate: String = "",
        var password: String = "",
        var username: String = "",
        var realName: String = ""
)