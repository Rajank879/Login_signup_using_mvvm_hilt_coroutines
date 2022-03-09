package com.appilab.loginsignupmvvm.di

import com.appilab.loginsignupmvvm.data.remote.AuthService
import com.appilab.loginsignupmvvm.data.remote.AuthTokenRemoteSource
import com.appilab.loginsignupmvvm.data.remote.AuthTokenRemoteSourceImple
import com.appilab.loginsignupmvvm.data.remote.model.AuthTokenDtoMapper
import com.appilab.loginsignupmvvm.util.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideAuthTokenDtoMapper(): AuthTokenDtoMapper{
        return AuthTokenDtoMapper()
    }

    @Singleton
    @Provides
    fun provideAuthTokenRemoteSource(
        authService: AuthService
    ):AuthTokenRemoteSource{
        return AuthTokenRemoteSourceImple(authService)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,
    moshi: Moshi):Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthService(retrofit: Retrofit):AuthService{
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoshi():Moshi = Moshi.Builder()
        .build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}