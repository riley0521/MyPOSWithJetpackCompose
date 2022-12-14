package com.rpfcoding.myposwithjetpackcompose.di

import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiAuthEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiBusinessEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiProductEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiUserEndpoints
import com.rpfcoding.myposwithjetpackcompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiAuthEndpoints(
        retrofit: Retrofit
    ): ApiAuthEndpoints = retrofit.create()

    @Provides
    @Singleton
    fun provideApiBusinessEndpoints(
        retrofit: Retrofit
    ): ApiBusinessEndpoints = retrofit.create()

    @Provides
    @Singleton
    fun provideApiProductEndpoints(
        retrofit: Retrofit
    ): ApiProductEndpoints = retrofit.create()

    @Provides
    @Singleton
    fun provideApiUserEndpoints(
        retrofit: Retrofit
    ): ApiUserEndpoints = retrofit.create()
}