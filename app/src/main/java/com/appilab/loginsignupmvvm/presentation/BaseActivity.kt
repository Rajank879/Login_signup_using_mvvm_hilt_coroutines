package com.appilab.loginsignupmvvm.presentation

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity :AppCompatActivity(){

    abstract fun displayProgressBar(loading: Boolean)
}