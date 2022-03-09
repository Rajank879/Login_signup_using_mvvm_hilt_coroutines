package com.appilab.loginsignupmvvm.data.remote

import com.appilab.loginsignupmvvm.data.remote.model.AuthTokenDto
import com.appilab.loginsignupmvvm.data.remote.requests.AuthLoginRequest
import com.appilab.loginsignupmvvm.data.remote.requests.AuthRegistrationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AuthTokenRemoteSourceImple @Inject constructor
    (private val authService: AuthService):AuthTokenRemoteSource {
    override suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<Response<AuthTokenDto>> =
        flow {
            val result = authService.login(authLoginRequest)
            Timber.d("loginAuthToken imple: ${result.code()}")
            Timber.d("loginAuthToken imple: ${result.message()}")
            Timber.d("loginAuthToken imple: ${result.body()}")
            Timber.d("loginAuthToken imple: ${result.raw()}")
            Timber.d("loginAuthToken imple: ${result.isSuccessful()}")
            Timber.d("loginAuthToken imple: ${result.errorBody()}")
        }

    override suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest): Flow<Response<AuthTokenDto>> =
        flow {
            val result = authService.register(authRegistrationRequest)
            Timber.d("registrationAuthToken imple: ${result.raw()}")
            Timber.d("registrationAuthToken imple: ${result.message()}")
            Timber.d("registrationAuthToken imple: ${result.body()}")
            Timber.d("registrationAuthToken imple: ${result.raw()}")
            Timber.d("registrationAuthToken imple: ${result.isSuccessful()}")
            Timber.d("registrationAuthToken imple: ${result.errorBody()}")
        }
}