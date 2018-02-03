package com.wh2.reduxLogin.util

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

fun showShortMessage(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun showLongMessage(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

fun showMessage(view: View, message: String) {
    Snackbar.make(view, message, if (message.length > 20) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG).show()
}

fun watchTextChanges(view: TextView): Observable<String> {
    return RxTextView.textChanges(view).map { it.toString() }
}