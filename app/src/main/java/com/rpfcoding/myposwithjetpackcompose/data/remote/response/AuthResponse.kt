package com.rpfcoding.myposwithjetpackcompose.data.remote.response

data class AuthResponse(
    val userId: Int,
    val businessId: Int,
    val token: String,
)
