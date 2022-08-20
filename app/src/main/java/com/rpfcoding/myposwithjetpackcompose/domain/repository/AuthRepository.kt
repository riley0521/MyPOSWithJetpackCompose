package com.rpfcoding.myposwithjetpackcompose.domain.repository

import com.rpfcoding.myposwithjetpackcompose.util.Resource

interface AuthRepository {

    suspend fun login(username: String, password: String): Resource<Unit>

    suspend fun isAuthenticated(token: String): Resource<Unit>

    suspend fun register(
        username: String,
        password: String,
        confirmPass: String,
        firstName: String,
        middleName: String,
        lastName: String,
        email: String
    ): Resource<Unit>
}