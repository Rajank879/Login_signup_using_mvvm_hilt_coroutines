package com.appilab.loginsignupmvvm.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.appilab.loginsignupmvvm.R
import com.appilab.loginsignupmvvm.databinding.ActivityAuthBinding
import com.appilab.loginsignupmvvm.domain.models.ResponseType
import com.appilab.loginsignupmvvm.domain.models.StateResponses
import com.appilab.loginsignupmvvm.presentation.displayErrorDialog
import com.appilab.loginsignupmvvm.presentation.displayToast
import com.appilab.loginsignupmvvm.presentation.ui.BaseActivity
import com.appilab.loginsignupmvvm.presentation.ui.MainActivity
import com.appilab.loginsignupmvvm.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

  private  lateinit var navController: NavController
  private lateinit var binding: ActivityAuthBinding
  private val authViewModel:AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()

        subscibreObservers()
        checkPreviousAuthUser()
    }

    private fun subscibreObservers(){
        authViewModel.loginResult.observe(this){ event ->
            event.getContentIfNotHandled()?.let { result->
                result.let {
                    when(result.status){
                        Status.LOADING -> displayProgressBar(true)

                        Status.SUCCESS->{
                            displayProgressBar(false)
                            result.data?.let {
                                sessionManager.login(it)
                            }
                        }

                        Status.ERROR -> {
                            displayProgressBar(false)
                            result.data?.let {
                                it.errorResponses?.let {
                                    handleErrorResponse(it)
                                }
                            }
                        }
                    }
                }
            }

        }

        sessionManager.cachedToken.observe(this){
            if (it?.token!=null){
                navMainActivity()
            }
        }
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPreviousAuthUser(){
        authViewModel.checkPreviosAuthUser()
    }

    private fun handleErrorResponse(responses: StateResponses) {
        when(responses.errorResponseType)
        {
            is ResponseType.Toast ->{
                responses.message?.let{
                    displayToast(it)
                }
            }

            is ResponseType.Dialog ->{
                responses.message?.let {
                    displayErrorDialog(it)
                }
            }

            is ResponseType.None ->{

            }
        }

    }

    override fun displayProgressBar(loading: Boolean) {

        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE

    }
}