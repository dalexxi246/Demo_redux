package com.wh2soft.footy.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun disposeAll(vararg disposables: Disposable) {
    disposables
            .filterNot { it.isDisposed }
            .forEach { it.dispose() }
}

fun dispose(compositeDisposable: CompositeDisposable) {
    if (!compositeDisposable.isDisposed) {
        compositeDisposable.dispose()
    }
}