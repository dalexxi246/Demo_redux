package com.wh2soft.footy.ui.viewmodels

import com.brianegan.bansa.Store
import com.wh2soft.footy.domain.model.User
import com.wh2soft.footy.redux.actions.*
import com.wh2soft.footy.redux.states.ApplicationState
import com.wh2soft.footy.redux.states.AuthState
import com.wh2soft.footy.util.ActionLiveData
import com.wh2soft.footy.util.ValidationError
import com.wh2soft.footy.util.Validator

class AuthViewModel(store: Store<ApplicationState>) : ReduxViewModel(store) {

    var firstName = ""
        set(value) {
            field = value
            getEmptyFieldValidator(field)
                    .addErrorCallback { Form.formValidations.firstNameValidationError = it }
                    .addSuccessCallback { Form.formValidations.firstNameValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }

    var lastName = ""
        set(value) {
            field = value
            getEmptyFieldValidator(field)
                    .addErrorCallback { Form.formValidations.lastNameValidationError = it }
                    .addSuccessCallback { Form.formValidations.lastNameValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }

    var email = ""
        set(value) {
            field = value
            getEmailValidator(field)
                    .addErrorCallback { Form.formValidations.emailValidationError = it }
                    .addSuccessCallback { Form.formValidations.emailValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }

    var username = ""
        set(value) {
            field = value
            getUserNameValidator(field)
                    .addErrorCallback { Form.formValidations.usernameValidationError = it }
                    .addSuccessCallback { Form.formValidations.usernameValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }

    var password = ""
        set(value) {
            field = value
            getPasswordValidator(field)
                    .addErrorCallback { Form.formValidations.passwordValidationError = it }
                    .addSuccessCallback { Form.formValidations.passwordValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }

    var passwordConfirm = ""
        set(value) {
            field = value
            getPasswordMatchValidator(password, field)
                    .addErrorCallback { Form.formValidations.passwordConfirmValidationError = it }
                    .addSuccessCallback { Form.formValidations.passwordConfirmValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }

    var birthday = ""
        set(value) {
            field = value
            getEmptyFieldValidator(field)
                    .addErrorCallback { Form.formValidations.birthdayValidationError = it }
                    .addSuccessCallback { Form.formValidations.birthdayValidationError = null }
                    .addFinallyCallback { afterValidateForm() }
                    .validate()
        }
    private fun afterValidateForm() {
        formValidationsSubject.sendAction(Form.formValidations)
    }

    var btnEnabled = false

    class FormValidations(
            var firstNameValidationError: ValidationError? = null,
            var lastNameValidationError: ValidationError? = null,
            var emailValidationError: ValidationError? = null,
            var usernameValidationError: ValidationError? = null,
            var passwordValidationError: ValidationError? = null,
            var passwordConfirmValidationError: ValidationError? = null,
            var birthdayValidationError: ValidationError? = null
    ) {

        fun isFormReadyForSignUp() =
                firstNameValidationError == null &&
                lastNameValidationError == null &&
                emailValidationError == null &&
                usernameValidationError == null &&
                passwordValidationError == null &&
                passwordConfirmValidationError == null &&
                birthdayValidationError == null

        fun isFormReadyForLogin() = usernameValidationError == null && passwordValidationError == null

    }

    object Form {
        val formValidations = FormValidations()
    }

    val formValidationsSubject: ActionLiveData<FormValidations> = ActionLiveData()
    val stateSubject: ActionLiveData<AuthState> = ActionLiveData()

    override fun render(state: ApplicationState) {
        stateSubject.sendAction(state.authState)
    }

    fun performSignUp() {
        if (Form.formValidations.isFormReadyForSignUp()) {
            val user = User(email)
            user.realName = "$firstName $lastName"
            user.birthdate = birthday
            store.dispatch(signUp(user, password))
        }
    }

    fun performLoginWithEmail() {
        if (Form.formValidations.isFormReadyForLogin()) {
            store.dispatch(login(email, password))
        }
    }

    fun changeToRegister() {
        store.dispatch(CHANGE_TO_REGISTER)
    }

    fun changeToLogin() {
        store.dispatch(CHANGE_TO_LOGIN)
    }

    private fun getEmptyFieldValidator(value: String) =
            Validator(value)
                    .nonEmpty()

    private fun getPasswordValidator(value: String) =
            Validator(value)
                    .isSecurePassword()

    private fun getPasswordMatchValidator(password: String, passwordConfirm: String) =
            Validator(password)
                    .nonEmpty()
                    .matchWith(passwordConfirm)

    private fun getUserNameValidator(username: String) =
            Validator(username)
                    .startsWithNonNumber()

    private fun getEmailValidator(email: String) =
            Validator(email)
                    .email()
                    .nonEmpty()
}
