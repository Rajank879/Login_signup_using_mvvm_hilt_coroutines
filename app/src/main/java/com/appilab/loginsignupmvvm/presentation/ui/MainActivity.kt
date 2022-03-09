package com.appilab.loginsignupmvvm.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appilab.loginsignupmvvm.R
import com.appilab.loginsignupmvvm.databinding.ActivityMainBinding
import com.appilab.loginsignupmvvm.presentation.ui.auth.AuthActivity

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    override fun displayProgressBar(loading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener {
            sessionManager.logout()
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.cachedToken.observe(this){
            if (it?.token!=null){
                val intent = Intent(this,AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}