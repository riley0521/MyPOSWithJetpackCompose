package com.rpfcoding.myposwithjetpackcompose.data.remote.response

data class PaginatedResponse<T>(
    val page: Int,
    val totalCount: Int,
    val result: List<T>
)
