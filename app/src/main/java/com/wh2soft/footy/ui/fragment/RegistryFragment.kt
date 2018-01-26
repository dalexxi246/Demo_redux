package com.wh2soft.footy.ui.fragment


import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.wh2soft.footy.R
import com.wh2soft.footy.databinding.FragmentRegisterBinding
import com.wh2soft.footy.redux.states.AuthState
import com.wh2soft.footy.ui.viewmodels.AuthViewModel
import com.wh2soft.footy.ui.viewmodels.factories.AuthViewModelFactory
import com.wh2soft.footy.util.*
import kotlinx.android.synthetic.main.view_register_email.view.*
import javax.inject.Inject

class RegistryFragment : ReduxFragment() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    private lateinit var fragmentRegisterBinding: FragmentRegisterBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        authViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel::class.java)
        fragmentRegisterBinding.vm = authViewModel

        setupBindings()

        return fragmentRegisterBinding.root
    }

    override fun onStart() {
        super.onStart()
        restoreUIData()
    }

    override fun restoreUIData() {
        fragmentRegisterBinding.formRegisterEmail.txt_firstname.setText(authViewModel.firstName)
        fragmentRegisterBinding.formRegisterEmail.txt_lastname.setText(authViewModel.lastName)
        fragmentRegisterBinding.formRegisterEmail.txt_email.setText(authViewModel.email)
        fragmentRegisterBinding.formRegisterEmail.txt_username.setText(authViewModel.username)
        fragmentRegisterBinding.formRegisterEmail.txt_password.setText(authViewModel.password)
        fragmentRegisterBinding.formRegisterEmail.txt_password_confirm.setText(authViewModel.passwordConfirm)
        fragmentRegisterBinding.formRegisterEmail.txt_birthdate.text = authViewModel.birthday
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        authViewModel.firstName = fragmentRegisterBinding.formRegisterEmail.txt_firstname.text.toString()
        authViewModel.lastName = fragmentRegisterBinding.formRegisterEmail.txt_lastname.text.toString()
        authViewModel.email = fragmentRegisterBinding.formRegisterEmail.txt_email.text.toString()
        authViewModel.username = fragmentRegisterBinding.formRegisterEmail.txt_username.text.toString()
        authViewModel.password = fragmentRegisterBinding.formRegisterEmail.txt_password.text.toString()
        authViewModel.passwordConfirm = fragmentRegisterBinding.formRegisterEmail.txt_password_confirm.text.toString()
        authViewModel.birthday = fragmentRegisterBinding.formRegisterEmail.txt_birthdate.text.toString()
    }

    private fun setupBindings() {

        disposables.add(RxView.clicks(fragmentRegisterBinding.formRegisterEmail.btn_register).subscribe { authViewModel.performSignUp() })

        disposables.add(RxView.clicks(fragmentRegisterBinding.formRegisterEmail.btn_select_birthdate).subscribe {
            buildDatePickerFromToday(context!!, DatePickerDialog.OnDateSetListener { picker, year, month, day ->
                run {
                    val dateAsString = getString(R.string.date_format, year, month + 1, day)
                    authViewModel.birthday = dateAsString
                    fragmentRegisterBinding.formRegisterEmail.txt_birthdate.text = dateAsString
                }
            }).show()
        })

        disposables.add(RxView.clicks(fragmentRegisterBinding.btnAccessEmail).subscribe { authViewModel.changeToLogin() })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_email).subscribe { authViewModel.email = it })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_username).subscribe { authViewModel.username = it })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_password).subscribe { authViewModel.password = it })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_password_confirm).subscribe { authViewModel.passwordConfirm = it })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_firstname).subscribe { authViewModel.firstName = it })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_lastname).subscribe { authViewModel.lastName = it })

        disposables.add(watchTextChanges(fragmentRegisterBinding.formRegisterEmail.txt_birthdate).subscribe { authViewModel.birthday = it })

        authViewModel.formValidationsSubject.observe(this, Observer { formValidations ->

            fragmentRegisterBinding.formRegisterEmail.txt_email_label.error =
                    when (formValidations?.emailValidationError) {
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        ValidationError.EMAIL -> getString(R.string.errmsg_invalid_email)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.txt_password_label.error =
                    when (formValidations?.passwordValidationError) {
                        ValidationError.SECURE_PASSWORD -> getString(R.string.errmsg_password_invalid)
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.txt_firstname_label.error =
                    when (formValidations?.firstNameValidationError) {
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.txt_lastname_label.error =
                    when (formValidations?.lastNameValidationError) {
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.txt_username_label.error =
                    when (formValidations?.usernameValidationError) {
                        ValidationError.NON_EMPTY, ValidationError.STARTS_WITH_NON_NUMBER -> getString(R.string.errmsg_username_invalid)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.txt_password_confirm_label.error =
                    when (formValidations?.passwordConfirmValidationError) {
                        ValidationError.MATCH_WITH -> getString(R.string.errmsg_password_unmatch)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.txt_birthdate_label.error =
                    when (formValidations?.birthdayValidationError) {
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        else -> null
                    }

            fragmentRegisterBinding.formRegisterEmail.btn_register.isEnabled = formValidations?.isFormReadyForSignUp() == true

        })

        authViewModel.stateSubject.observe(this, Observer { renderView(it) })
    }

    fun renderView(authState: AuthState?) {
        if (authState?.authStage == AuthState.AuthStage.SIGN_UP) {
            handleResult(authState.authResult)
        }
    }

    private fun handleResult(authResult: AuthState.AuthResult?) {
        when (authResult) {
            AuthState.AuthResult.REGISTER_FAILED -> showLongMessage(fragmentRegisterBinding.root, getString(R.string.errmsg_registration_failed))
            AuthState.AuthResult.PASSWORD_UNMATCH -> showShortMessage(fragmentRegisterBinding.root, getString(R.string.errmsg_password_unmatch))
            AuthState.AuthResult.SUCCESS_REGISTER -> showMessage(fragmentRegisterBinding.root, getString(R.string.message_account_created))
        }
    }

    override fun inject() {
        fragmentsComponent.inject(this)
    }

    companion object {

        fun newInstance(): RegistryFragment {
            return RegistryFragment()
        }

    }

}
