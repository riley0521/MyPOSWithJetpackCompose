package com.rpfcoding.myposwithjetpackcompose.di

import com.rpfcoding.myposwithjetpackcompose.data.remote.MyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.107:7009/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMyApi(
        retrofit: Retrofit
    ): MyApi = retrofit.create()
}