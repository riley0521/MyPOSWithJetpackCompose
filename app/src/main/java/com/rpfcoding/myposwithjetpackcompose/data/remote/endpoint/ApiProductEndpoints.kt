package com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint

import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.ProductDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.ProductGroupDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.request.ProductGroupRequest
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.CreatedFileNameResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.CreatedIdResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.PaginatedResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiProductEndpoints {

    @GET("ProductGroup/GetAll/{businessId}")
    suspend fun getAllProductGroup(
        @Path("businessId") businessId: Int
    ): List<ProductGroupDto>

    @POST("ProductGroup/Create")
    suspend fun createProductGroup(
        @Header("Authorization") token: String,
        @Body body: ProductGroupRequest
    ): CreatedIdResponse

    @POST("ProductGroup/UploadImage")
    @Multipart
    suspend fun uploadProductGroupImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): CreatedFileNameResponse

    @GET("Product/GetSome/{groupId}")
    suspend fun getSomeProduct(
        @Path("groupId") groupId: Int,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 25
    ): PaginatedResponse<ProductDto>

    @POST("Product/Create")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body body: ProductDto
    ): CreatedIdResponse

    @POST("Product/UploadImage")
    @Multipart
    suspend fun uploadProductImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): CreatedFileNameResponse
}