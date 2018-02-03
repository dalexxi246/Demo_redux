package com.wh2.reduxLogin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wh2.reduxLogin.Application
import com.wh2.reduxLogin.di.components.DaggerFragmentsComponent
import com.wh2.reduxLogin.di.components.FragmentsComponent
import com.wh2.reduxLogin.util.dispose
import io.reactivex.disposables.CompositeDisposable

abstract class ReduxFragment : Fragment() {

    companion object {
        @JvmStatic
        lateinit var fragmentsComponent: FragmentsComponent
    }

    protected lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        disposables = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentsComponent = DaggerFragmentsComponent.builder().applicationComponent(Application.applicationComponent).build()
        fragmentsComponent.inject(this)
        inject()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose(disposables)
    }

    protected abstract fun inject()

    protected abstract fun restoreUIData()

}