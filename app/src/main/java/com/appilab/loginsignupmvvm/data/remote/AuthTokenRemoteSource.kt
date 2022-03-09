package com.appilab.loginsignupmvvm.data.remote

import com.appilab.loginsignupmvvm.data.remote.model.AuthTokenDto
import com.appilab.loginsignupmvvm.data.remote.requests.AuthLoginRequest
import com.appilab.loginsignupmvvm.data.remote.requests.AuthRegistrationRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthTokenRemoteSource {

    suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<Response<AuthTokenDto>>

    suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest):Flow<Response<AuthTokenDto>>
}