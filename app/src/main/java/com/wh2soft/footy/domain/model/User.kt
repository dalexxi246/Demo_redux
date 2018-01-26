package com.wh2soft.footy.domain.model

data class User(
        var email: String = "",
        var uuid: String = "",
        var birthdate: String = "",
        var password: String = "",
        var username: String = "",
        var realName: String = ""
)