package com.wh2soft.footy.ui.views

import android.app.DatePickerDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.RelativeLayout
import com.wh2soft.footy.R
import com.wh2soft.footy.databinding.ViewRegisterEmailBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class RegisterFormView @JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0) : RelativeLayout(context, attrs, defStyle), DatePickerDialog.OnDateSetListener {

    private lateinit var viewRegisterEmailBinding: ViewRegisterEmailBinding
    private val layoutId = R.layout.view_register_email

    private val dateSelectedSubject = PublishSubject.create<String>()

    init {
        LayoutInflater.from(context).inflate(layoutId, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        viewRegisterEmailBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, this, false)
//        viewRegisterEmailBinding.btnSelectBirthdate.setOnClickListener { buildDatePicker18YearsAgo(context, this).show() }
    }

    override fun onDateSet(datePicker: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val dateAsString = "$p1 - $p2 - $p3"
        viewRegisterEmailBinding.txtBirthdate.text = dateAsString
        dateSelectedSubject.onNext(dateAsString)
    }

    fun observeDateSelected(): Observable<String> {
        return dateSelectedSubject.hide().subscribeOn(AndroidSchedulers.mainThread())
    }

}