package com.appilab.loginsignupmvvm.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appilab.loginsignupmvvm.R
import com.appilab.loginsignupmvvm.databinding.FragmentLauncherBinding

class LauncherFragment:Fragment(R.layout.fragment_launcher) {

    private lateinit var binding: FragmentLauncherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLauncherBinding.bind(view)

        binding.loginTxt.setOnClickListener {
            navlogin()
        }

        binding.registerTxt.setOnClickListener {
            navRegister()
        }
    }

    private fun navRegister() {
        findNavController().navigate(R.id.action_launcherfragment_to_registerfragment)
    }

    private fun navlogin() {
        findNavController().navigate(R.id.action_launcherfragment_to_login_fragment)
    }
}