package com.rpfcoding.myposwithjetpackcompose.domain.repository

import com.rpfcoding.myposwithjetpackcompose.util.Resource

interface AuthRepository {

    suspend fun login(username: String, password: String): Resource<Unit>

    suspend fun isAuthenticated(token: String): Resource<Unit>
}