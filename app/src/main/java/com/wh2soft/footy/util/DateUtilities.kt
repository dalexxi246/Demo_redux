package com.wh2soft.footy.util

import android.app.DatePickerDialog
import android.content.Context
import com.wh2soft.footy.R
import java.util.*

private fun getMaxDateYearsAgo(yearsAgo: Int): Long {
    val maxDate: Long
    val today = Date()
    val c = Calendar.getInstance()
    c.time = today
    if (yearsAgo > 0) {
        c.add(Calendar.YEAR, -yearsAgo)
    }
    maxDate = c.time.time
    return maxDate
}

private fun getMaxDateToday() = getMaxDateYearsAgo(0)

private fun buildDatePicker(context: Context, callBack: DatePickerDialog.OnDateSetListener, maxDate: Long): DatePickerDialog {

    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(context, R.style.DateDialogTheme, callBack, year, month, day)

    datePickerDialog.datePicker.maxDate = maxDate

    return datePickerDialog

}

fun buildDatePickerFromToday(context: Context, callBack: DatePickerDialog.OnDateSetListener) = buildDatePicker(context, callBack, getMaxDateToday())

fun buildDatePicker18YearsAgo(context: Context, callBack: DatePickerDialog.OnDateSetListener) = buildDatePicker(context, callBack, getMaxDateYearsAgo(18))