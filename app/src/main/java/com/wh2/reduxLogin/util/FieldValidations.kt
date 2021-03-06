package com.wh2.reduxLogin.util

import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.widget.EditText

/**
 * Created by gurleensethi on 08/09/17.
 * Add easy validations on edit text
 *
 * Motivation : Easily validate the text from the edit text based on different parameters
 */

fun EditText.validator(): Validator = Validator(text.toString())

fun TextInputEditText.validator(): Validator = Validator(text.toString())

/*
* Class to process all the filters provided by the user
* */
class Validator(val text: String) {

    /*
    * Boolean to determine whether all the validations has passed successfully.
    * If any validation fails the state is changed to false.
    * Final result is returned to the user
    * */
    private var isValidated = true

    /*
    * If validation fails then this callback is invoked to notify the user about
    * and error
    * */
    private var errorCallback: ((ValidationError?) -> Unit)? = null

    /*
    * If validation is passes then this callback is invoked to notify the user
    * for the same
    * */
    private var successCallback: (() -> Unit)? = null

    /**
     *
     */
    private var finallyCallback: (() -> Unit)? = null

    /*
    * User settable limits for the numbers of characters that the string can contain
    * */
    private var MINIMUM_LENGTH = 0
    private var MAXIMUM_LENGTH = Int.MAX_VALUE

    private var VALIDATION_ERROR_TYPE: ValidationError? = null

    fun validate(): Boolean {
        //Check if the string characters count is in limits
        if (text.length < MINIMUM_LENGTH) {
            isValidated = false
            setErrorType(ValidationError.MINIMUM_LENGTH)
        } else if (text.length > MAXIMUM_LENGTH) {
            isValidated = false
            setErrorType(ValidationError.MAXIMUM_LENGTH)
        }

        //Invoke the error callback if supplied by the user
        if (isValidated) {
            successCallback?.invoke()
        } else {
            errorCallback?.invoke(VALIDATION_ERROR_TYPE)
        }
        finallyCallback?.invoke()

        return isValidated
    }

    fun email(): Validator {
        if (!text.matches(Regex("([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}"))) {
            setErrorType(ValidationError.EMAIL)
        }
        return this
    }

    fun noNumbers(): Validator {
        if (text.matches(Regex(".*\\d.*"))) {
            setErrorType(ValidationError.NO_NUMBERS)
        }
        return this
    }

    fun nonEmpty(): Validator {
        if (text.isEmpty()) {
            setErrorType(ValidationError.NON_EMPTY)
        }
        return this
    }

    fun matchWith(another: String): Validator {
        if (text != another) {
            setErrorType(ValidationError.MATCH_WITH)
        }
        return this
    }

    fun onlyNumbers(): Validator {
        if (!text.matches(Regex("\\d+"))) {
            setErrorType(ValidationError.ONLY_NUMBERS)
        }
        return this
    }

    fun allUpperCase(): Validator {
        if (text.toUpperCase() != text) {
            setErrorType(ValidationError.ALL_UPPER_CASE)
        }
        return this
    }

    fun allLowerCase(): Validator {
        if (text.toLowerCase() != text) {
            setErrorType(ValidationError.ALL_LOWER_CASE)
        }
        return this
    }

    fun atLeastOneLowerCase(): Validator {
        if (text.matches(Regex("[A-Z0-9]+"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_LOWER_CASE)
        }
        return this
    }

    fun atLeastOneUpperCase(): Validator {
        if (text.matches(Regex("[a-z0-9]+"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_UPPER_CASE)
        }
        return this
    }

    fun maximumLength(length: Int): Validator {
        MAXIMUM_LENGTH = length
        return this
    }

    fun minimumLength(length: Int): Validator {
        MINIMUM_LENGTH = length
        return this
    }

    fun addErrorCallback(callback: (ValidationError?) -> Unit): Validator {
        errorCallback = callback
        return this
    }

    fun addSuccessCallback(callback: () -> Unit): Validator {
        successCallback = callback
        return this
    }

    fun addFinallyCallback(callback: () -> Unit): Validator {
        finallyCallback = callback
        return this
    }

    fun atLeastOneNumber(): Validator {
        if (!text.matches(Regex(".*\\d.*"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_NUMBER)
        }
        return this
    }

    fun startsWithNonNumber(): Validator {
        if (!TextUtils.isEmpty(text) && Character.isDigit(text[0])) {
            setErrorType(ValidationError.STARTS_WITH_NON_NUMBER)
        }
        return this
    }

    fun noSpecialCharacter(): Validator {
        if (!text.matches(Regex("[A-Za-z0-9]+"))) {
            setErrorType(ValidationError.NO_SPECIAL_CHARACTER)
        }
        return this
    }

    fun atLeastOneSpecialCharacter(): Validator {
        if (text.matches(Regex("[A-Za-z0-9]+"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_SPECIAL_CHARACTER)
        }
        return this
    }

    /**
     * Password with 6 - 20 chars, at least a letter and a number. Allows symbols
     */
    fun isSecurePassword(): Validator {
        if (!text.matches(Regex("(?=(.*[a-z])+)(?=(.*[0-9])+)[0-9a-zA-Z!\\\"#\$%&'()*+,\\-.\\/:;<=>?@\\[\\\\\\]^_`{|}~]{6,20}"))) {
            setErrorType(ValidationError.SECURE_PASSWORD)
        }
        return this
    }

    fun contains(string: String): Validator {
        if (!text.contains(string)) {
            setErrorType(ValidationError.CONTAINS)
        }
        return this
    }

    fun doesNotContains(string: String): Validator {
        if (text.contains(string)) {
            setErrorType(ValidationError.DOES_NOT_CONTAINS)
        }
        return this
    }

    fun startsWith(string: String): Validator {
        if (!text.startsWith(string)) {
            setErrorType(ValidationError.STARTS_WITH)
        }
        return this
    }

    fun endsWith(string: String): Validator {
        if (!text.endsWith(string)) {
            setErrorType(ValidationError.ENDS_WITH)
        }
        return this
    }

    private fun setErrorType(validationError: ValidationError) {
        isValidated = false
        if (VALIDATION_ERROR_TYPE == null) {
            VALIDATION_ERROR_TYPE = validationError
        }
    }
}

/*
* Enums that serve for identification of error while validation text.
* Every enum is the name of a function with the corresponding validation
* */
enum class ValidationError {
    MINIMUM_LENGTH,
    MAXIMUM_LENGTH,
    AT_LEAST_ONE_UPPER_CASE,
    AT_LEAST_ONE_LOWER_CASE,
    ALL_LOWER_CASE,
    ALL_UPPER_CASE,
    ONLY_NUMBERS,
    NON_EMPTY,
    NO_NUMBERS,
    EMAIL,
    AT_LEAST_ONE_NUMBER,
    STARTS_WITH_NON_NUMBER,
    NO_SPECIAL_CHARACTER,
    AT_LEAST_ONE_SPECIAL_CHARACTER,
    CONTAINS,
    DOES_NOT_CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
    SECURE_PASSWORD,
    MATCH_WITH
}