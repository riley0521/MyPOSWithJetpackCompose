package com.rpfcoding.myposwithjetpackcompose.data.remote

import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.AuthResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.LoginDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiAuthEndpoints {

    @POST("User/Login")
    suspend fun login(
        @Body info: LoginDto
    ): AuthResponse

    @POST("User/Test")
    suspend fun isAuthenticated(
        @Header("Authorization") token: String
    )
}