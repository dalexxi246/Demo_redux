package com.wh2.reduxLogin.ui.views

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.wh2.reduxLogin.R
import com.wh2.reduxLogin.databinding.ViewLoginBinding

class LoginFormView @JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0) : RelativeLayout(context, attrs, defStyle) {

    private lateinit var viewLoginBinding: ViewLoginBinding

    init {
        LayoutInflater.from(context).inflate(R.layout.view_login, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        viewLoginBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_login, this, false)
    }

}