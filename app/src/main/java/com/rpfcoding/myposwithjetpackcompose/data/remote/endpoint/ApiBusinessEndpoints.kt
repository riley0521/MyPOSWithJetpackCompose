package com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint

import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.BusinessDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.CreatedIdResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.CreatedFileNameResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiBusinessEndpoints {

    @POST("Business/Create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body businessDto: BusinessDto
    ): CreatedIdResponse

    @POST("Business/UploadImage")
    @Multipart
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): CreatedFileNameResponse
}