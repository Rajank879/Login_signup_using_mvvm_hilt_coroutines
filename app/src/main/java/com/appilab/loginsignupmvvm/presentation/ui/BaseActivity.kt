package com.appilab.loginsignupmvvm.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import com.appilab.loginsignupmvvm.session.SessionManager
import javax.inject.Inject

abstract class BaseActivity :AppCompatActivity(){

    @Inject
    lateinit var sessionManager: SessionManager

    abstract fun displayProgressBar(loading: Boolean)
}