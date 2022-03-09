package com.appilab.loginsignupmvvm.presentation.ui.auth

import android.content.Context
import androidx.lifecycle.*
import com.appilab.loginsignupmvvm.data.remote.requests.AuthLoginRequest
import com.appilab.loginsignupmvvm.data.remote.requests.AuthRegistrationRequest
import com.appilab.loginsignupmvvm.domain.models.*
import com.appilab.loginsignupmvvm.repository.AuthRepository
import com.appilab.loginsignupmvvm.util.Event
import com.appilab.loginsignupmvvm.util.Resource
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _loginCredentials = MutableLiveData<LoginCredentials>()
    val loginCredentials :LiveData<LoginCredentials> = _loginCredentials

    private val _registrationCredentials = MutableLiveData<RegistrationCredentials>()
    val registrationCredentials :LiveData<RegistrationCredentials> = _registrationCredentials

    private val _loginResult = MutableLiveData<Event<Resource<AuthToken>>>()
    val loginResult:LiveData<Event<Resource<AuthToken>>> = _loginResult

    fun login(email : String, password : String){
        viewModelScope.launch {
            _loginResult.postValue(Event(Resource.loading(null)))
            authRepository.login(
                AuthLoginRequest(
                    email = email,
                    password = password
                )
            ).collect {
                _loginResult.postValue(Event(it))
            }
        }
    }

    fun register(email: String,password: String){
        viewModelScope.launch {
            _loginResult.postValue(Event(Resource.loading(null)))
            authRepository.register(
                AuthRegistrationRequest(
                    email = email,
                    password = password
                )
            ).collect {
                _loginResult.postValue(Event(it))
            }
        }
    }

    fun checkPreviosAuthUser(){
        viewModelScope.launch {
            _loginResult.postValue(Event(Resource.loading(null)))
            authRepository.checkPreviousAuthUser().collect {
                _loginResult.postValue(Event(it))
            }
        }
    }

    fun setLoginFileds(email: String?, password:String?){
        _loginCredentials.value = LoginCredentials(email, password)
    }

    fun setRegistrationFields(email: String,password: String)
    {
        _registrationCredentials.value = RegistrationCredentials(email, password)
    }

    fun isValidForLogin(loginEmail:String?,loginPassword:String?):Boolean{

        if (loginEmail.isNullOrEmpty()
            ||loginEmail.isNullOrBlank()
            ||loginPassword.isNullOrBlank()
            ||loginPassword.isNullOrEmpty()
        ){
            postErrorValue("Either email or password is empty")
            return false
        }

        if (loginPassword.length < 5){
            postErrorValue("Password must be at least 6 characters long")
            return false
        }
        return true
    }

    fun isValidForRegistration(
        registrationEmail:String?,
        registrationPassword: String?,
        registrationConfirmpassword: String?
    ):Boolean{
        if (registrationEmail.isNullOrEmpty()
            ||registrationEmail.isNullOrBlank()
            ||registrationPassword.isNullOrBlank()
            ||registrationPassword.isNullOrEmpty()
            ||registrationConfirmpassword.isNullOrEmpty()
            ||registrationConfirmpassword.isNullOrBlank())
        {
            postErrorValue("Either email or password is empty")
            return false
        }

        if (registrationPassword.length<5 || registrationConfirmpassword.length<5)
        {
            postErrorValue("Password must be at least 6 characters long")
            return false
        }
        if (registrationPassword != registrationConfirmpassword){
            postErrorValue("Password do not match")
            return false
        }
        return true
    }

    private fun postErrorValue(errorMessage :  String){
        _loginResult.postValue(
            Event(
                Resource.error(errorMessage,
                AuthToken(
                    errorResponses = StateResponses(
                        message = errorMessage,
                        errorResponseType = ResponseType.Dialog
                    )
                )
                )
            )
        )
    }

}