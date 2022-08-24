package com.rpfcoding.myposwithjetpackcompose.di

import com.rpfcoding.myposwithjetpackcompose.data.repository.*
import com.rpfcoding.myposwithjetpackcompose.domain.repository.*
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

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}