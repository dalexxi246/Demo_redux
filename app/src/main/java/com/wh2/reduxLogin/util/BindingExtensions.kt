package com.wh2.reduxLogin.util

import android.databinding.ObservableField

fun ObservableField<String>.setAndValidate(string: String, validator: Validator) {
    this.set(string)
    validator.validate()
}