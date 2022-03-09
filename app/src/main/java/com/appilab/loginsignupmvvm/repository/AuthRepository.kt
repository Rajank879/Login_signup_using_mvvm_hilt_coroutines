package com.appilab.loginsignupmvvm.repository

import com.appilab.loginsignupmvvm.data.remote.requests.AuthLoginRequest
import com.appilab.loginsignupmvvm.data.remote.requests.AuthRegistrationRequest
import com.appilab.loginsignupmvvm.domain.models.AuthToken
import com.appilab.loginsignupmvvm.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(authLoginRequest: AuthLoginRequest):Flow<Resource<AuthToken>>

    suspend fun register(authRegistrationRequest: AuthRegistrationRequest):Flow<Resource<AuthToken>>

    suspend fun checkPreviousAuthUser():Flow<Resource<AuthToken>>
}