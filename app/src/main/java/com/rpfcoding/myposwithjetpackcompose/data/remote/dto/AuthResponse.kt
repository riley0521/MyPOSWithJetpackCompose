package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class AuthResponse(
    val userId: Int,
    val businessId: Int,
    val token: String,
)
