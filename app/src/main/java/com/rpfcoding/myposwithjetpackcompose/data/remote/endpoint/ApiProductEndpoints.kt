package com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint

import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.ProductDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.ProductGroupDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiProductEndpoints {

    @GET("ProductGroup/GetAll/{businessId}")
    suspend fun getAllProductGroup(
        @Path("businessId") businessId: Int
    ): List<ProductGroupDto>

    @GET("Product/GetSome/{groupId}")
    suspend fun getSomeProduct(
        @Path("groupId") groupId: Int,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 25
    ): PaginatedResponse<ProductDto>
}