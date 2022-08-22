package com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint

import com.rpfcoding.myposwithjetpackcompose.data.remote.response.AuthResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.LoginDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.RegisterUserDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.UserInfoResponse
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

    @POST("User/Register")
    suspend fun register(
        @Body registerUserDto: RegisterUserDto
    ): AuthResponse

    @POST("User/DownloadInfo")
    suspend fun downloadInfo(
        @Header("Authorization") token: String
    ): UserInfoResponse
}