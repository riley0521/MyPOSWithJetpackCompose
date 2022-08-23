package com.rpfcoding.myposwithjetpackcompose.di

import com.rpfcoding.myposwithjetpackcompose.data.repository.MyPreferencesRepositoryImpl
import com.rpfcoding.myposwithjetpackcompose.data.repository.AuthRepositoryImpl
import com.rpfcoding.myposwithjetpackcompose.data.repository.BusinessRepositoryImpl
import com.rpfcoding.myposwithjetpackcompose.data.repository.ModuleRepositoryImpl
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.BusinessRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ModuleRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Suppress("unused")
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindBusinessRepository(
        businessRepositoryImpl: BusinessRepositoryImpl
    ): BusinessRepository

    @Suppress("unused")
    @Binds
    abstract fun bindMyPreferencesRepository(
        myPreferencesRepositoryImpl: MyPreferencesRepositoryImpl
    ): MyPreferencesRepository

    @Binds
    abstract fun bindModuleRepository(
        moduleRepositoryImpl: ModuleRepositoryImpl
    ): ModuleRepository
}