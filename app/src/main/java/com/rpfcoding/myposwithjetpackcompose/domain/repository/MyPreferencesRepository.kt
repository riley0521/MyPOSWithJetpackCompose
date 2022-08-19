package com.rpfcoding.myposwithjetpackcompose.domain.repository

import kotlinx.coroutines.flow.Flow

interface MyPreferencesRepository {

    suspend fun saveUserId(userId: Int)
    fun readUserId(): Flow<Int>

    suspend fun saveBusinessId(businessId: Int)
    fun readBusinessId(): Flow<Int>

    suspend fun saveToken(token: String)
    fun readToken(): Flow<String>
}