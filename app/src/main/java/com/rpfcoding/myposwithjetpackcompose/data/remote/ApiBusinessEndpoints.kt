package com.rpfcoding.myposwithjetpackcompose.data.remote

import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.AuthResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.BusinessDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiBusinessEndpoints {

    @POST("Business/Create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body businessDto: BusinessDto
    ): AuthResponse
}