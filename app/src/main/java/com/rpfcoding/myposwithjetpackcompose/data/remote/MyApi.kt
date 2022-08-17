package com.rpfcoding.myposwithjetpackcompose.data.remote

import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.AuthResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.LoginDto
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    @GET("User/Login")
    suspend fun login(info: LoginDto): Response<AuthResponse>
}