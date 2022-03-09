package com.appilab.loginsignupmvvm.presentation.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.appilab.loginsignupmvvm.R
import com.appilab.loginsignupmvvm.databinding.FragmentLoginBinding
import com.appilab.loginsignupmvvm.domain.models.LoginCredentials

class LoginFragment  : BaseAuthFragment(R.layout.fragment_login) {

    lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentLoginBinding.bind(view)

        binding.usernameLogin.addTextChangedListener(afterTextChangedListener)
        binding.passwordLogin.addTextChangedListener(afterTextChangedListener)

        binding.loginButton.setOnClickListener {
            loginUser(binding.usernameLogin.text.toString(),
                binding.passwordLogin.text.toString())
        }

        subscribeObserves()
    }

    private val afterTextChangedListener = object:TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val emailInput = binding.usernameLogin.text.toString().trim()
            val passwordInput = binding.passwordLogin.text.toString().trim()

            binding.loginButton.isEnabled =  (emailInput.isNullOrBlank()
                    && emailInput.isNullOrBlank()
                    && passwordInput.isNullOrBlank()
                    && passwordInput.isNullOrEmpty())
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    private fun loginUser(email: String?, password:String?){
        if (authViewModel.isValidForLogin(email,password)){
            authViewModel.login(email!!,password!!)
        }
    }

    private fun subscribeObserves(){
        authViewModel.loginCredentials.observe(viewLifecycleOwner) {
            it?.let {
                setLoginCredentialsFields(it)
            }
        }
    }


    private fun setLoginCredentialsFields(loginCredentials: LoginCredentials)
    {
        binding.apply {
            usernameLogin.setText(loginCredentials.email)
            passwordLogin.setText(loginCredentials.password)

        }
    }
}