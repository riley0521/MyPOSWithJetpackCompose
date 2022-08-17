package com.rpfcoding.myposwithjetpackcompose.di

import com.rpfcoding.myposwithjetpackcompose.data.repository.RemoteAuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(remoteAuthRepository: RemoteAuthRepository): AuthRepository
}