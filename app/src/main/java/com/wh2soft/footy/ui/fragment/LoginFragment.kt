package com.wh2soft.footy.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wh2soft.footy.R
import com.wh2soft.footy.databinding.FragmentLoginBinding
import com.wh2soft.footy.redux.states.AuthState
import com.wh2soft.footy.ui.viewmodels.AuthViewModel
import com.wh2soft.footy.ui.viewmodels.factories.AuthViewModelFactory
import com.wh2soft.footy.util.ValidationError
import com.wh2soft.footy.util.showLongMessage
import com.wh2soft.footy.util.showMessage
import kotlinx.android.synthetic.main.view_login.view.*
import javax.inject.Inject

class LoginFragment : ReduxFragment() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        authViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel::class.java)
        fragmentLoginBinding.vm = authViewModel

        setupBindings()

        return fragmentLoginBinding.root
    }

    override fun onStart() {
        super.onStart()
        restoreUIData()
    }

    override fun restoreUIData() {
        fragmentLoginBinding.formLogin.txt_email.setText(authViewModel.email)
        fragmentLoginBinding.formLogin.txt_password.setText(authViewModel.password)
        fragmentLoginBinding.formLogin.btn_login_signin.isEnabled = authViewModel.btnEnabled
    }

    private fun setupBindings() {

        disposables.add(RxView.clicks(fragmentLoginBinding.formLogin.btn_login_signin).subscribe { authViewModel.performLoginWithEmail() })

        disposables.add(RxView.clicks(fragmentLoginBinding.linkSuggestRegister).subscribe { authViewModel.changeToRegister() })

        disposables.add(
                RxTextView.textChanges(fragmentLoginBinding.formLogin.txt_email)
                        .map { it.toString() }
                        .subscribe { authViewModel.email = it }
        )

        disposables.add(
                RxTextView.textChanges(fragmentLoginBinding.formLogin.txt_password)
                        .map { it.toString() }
                        .subscribe { authViewModel.password = it }
        )

        authViewModel.formValidationsSubject.observe(this, Observer { formValidations ->

            fragmentLoginBinding.formLogin.txt_email_label.error =
                    when (formValidations?.emailValidationError) {
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        ValidationError.EMAIL -> getString(R.string.errmsg_invalid_email)
                        else -> null
                    }

            fragmentLoginBinding.formLogin.txt_password_label.error =
                    when (formValidations?.passwordValidationError) {
                        ValidationError.SECURE_PASSWORD -> getString(R.string.errmsg_password_invalid)
                        ValidationError.NON_EMPTY -> getString(R.string.errmsg_field_required)
                        else -> null
                    }

            fragmentLoginBinding.formLogin.btn_login_signin.isEnabled = formValidations?.isFormReadyForLogin() == true

        })

        authViewModel.stateSubject.observe(this, Observer { renderView(it) })

    }

    private fun renderView(authState: AuthState?) {
        if (authState?.authStage == AuthState.AuthStage.SIGN_IN) {
            handleResult(authState.authResult)
        }
    }

    private fun handleResult(authResult: AuthState.AuthResult?) {
        when (authResult) {
            AuthState.AuthResult.LOGIN_FAILED -> showLongMessage(fragmentLoginBinding.root, getString(R.string.errmsg_login_failed))
            AuthState.AuthResult.SUCCESS_LOGIN -> showMessage(fragmentLoginBinding.root, getString(R.string.message_accesing))
        }
    }

    override fun inject() {
        fragmentsComponent.inject(this)
    }

    companion object {

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }

    }

}
