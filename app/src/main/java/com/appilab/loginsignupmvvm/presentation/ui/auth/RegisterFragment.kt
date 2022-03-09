package com.appilab.loginsignupmvvm.presentation.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.appilab.loginsignupmvvm.R
import com.appilab.loginsignupmvvm.databinding.FragmentRegisterBinding
import com.appilab.loginsignupmvvm.domain.models.RegistrationCredentials

class RegisterFragment : BaseAuthFragment(R.layout.fragment_register) {
   private lateinit var binding:FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.usernameRegister.addTextChangedListener(afterTextChangedListener)
        binding.passwordRegister.addTextChangedListener(afterTextChangedListener)
        binding.passwordConfirmRegister.addTextChangedListener(afterTextChangedListener)

        binding.registerButton.setOnClickListener {
            registerUser(binding.usernameRegister.text.toString(),
            binding.passwordRegister.text.toString(),
            binding.passwordConfirmRegister.text.toString())
        }

        subscribeObserves()

    }

    private fun subscribeObserves() {
        authViewModel.registrationCredentials.observe(viewLifecycleOwner) {
            it?.let {
                setRegistrationCredentialsFields(it)
            }
        }
    }

    private fun setRegistrationCredentialsFields(registrationCredentials: RegistrationCredentials){
        binding.apply {
            usernameRegister.setText(registrationCredentials.email)
            passwordRegister.setText(registrationCredentials.password)
        }
    }

    private fun registerUser(email:String?, password:String?, confirmPassword:String?){
        if (authViewModel.isValidForRegistration(email,password, confirmPassword))
        {
            authViewModel.register(email!!,password!!)
        }
    }



    private val afterTextChangedListener = object:TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val emailInput: String = binding.usernameRegister.text.toString()
            val passwordInput:String = binding.passwordRegister.text.toString()
            val confirmPasswordInput = binding.passwordConfirmRegister.text.toString()

            binding.registerButton.isEnabled = (emailInput.isNullOrEmpty()
                    && emailInput.isNullOrBlank()
                    && passwordInput.isNullOrEmpty()
                    && passwordInput.isNullOrBlank()
                    && confirmPasswordInput.isNullOrBlank()
                    && confirmPasswordInput.isNullOrEmpty()
            )

        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.setRegistrationFields(binding.usernameRegister.text.toString(),
        binding.passwordRegister.text.toString())
    }
}