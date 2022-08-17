package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class AuthResponse(
    val user: UserDto,
    val token: String,
    val statusCode: Int
)
