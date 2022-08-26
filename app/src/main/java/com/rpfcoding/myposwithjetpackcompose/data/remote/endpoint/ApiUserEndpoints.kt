package com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint

import com.rpfcoding.myposwithjetpackcompose.data.remote.request.UpdateUserRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface ApiUserEndpoints {

    @PUT("User/Update")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body body: UpdateUserRequest
    )
}