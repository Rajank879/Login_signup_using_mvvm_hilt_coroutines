package com.appilab.loginsignupmvvm.data.remote

import com.appilab.loginsignupmvvm.data.remote.model.AuthTokenDto
import com.appilab.loginsignupmvvm.data.remote.requests.AuthLoginRequest
import com.appilab.loginsignupmvvm.data.remote.requests.AuthRegistrationRequest
import com.appilab.loginsignupmvvm.domain.models.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/register")
    suspend fun register(
        @Body registrationRequest: AuthRegistrationRequest
    ):Response<AuthToken>

    suspend fun login(
        @Body loginRequest: AuthLoginRequest
    ):Response<AuthTokenDto>
}