package com.appilab.loginsignupmvvm.di

import android.content.Context
import com.appilab.loginsignupmvvm.data.local.AuthTokenDao
import com.appilab.loginsignupmvvm.data.local.AutoAuthPrefsManager
import com.appilab.loginsignupmvvm.data.local.model.AuthTokenEntityMapper
import com.appilab.loginsignupmvvm.data.remote.AuthTokenRemoteSource
import com.appilab.loginsignupmvvm.data.remote.model.AuthTokenDtoMapper
import com.appilab.loginsignupmvvm.repository.AuthRepository
import com.appilab.loginsignupmvvm.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAutoAuthPrefsManager(@ApplicationContext appContext:Context):AutoAuthPrefsManager{
        return AutoAuthPrefsManager(appContext)
    }

    fun provideAuthRepository(
        authTokenRemoteSource: AuthTokenRemoteSource,
        authTokenDao: AuthTokenDao,
        authTokenDtoMapper: AuthTokenDtoMapper,
        authTokenEntityMapper: AuthTokenEntityMapper,
        autoAuthPrefsManager: AutoAuthPrefsManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            authTokenRemoteSource = authTokenRemoteSource,
            authTokenDao = authTokenDao,
            authTokenDtoMapper = authTokenDtoMapper,
            authTokenEntityMapper = authTokenEntityMapper,
            autoAuthPrefsManager = autoAuthPrefsManager
        )
    }
}