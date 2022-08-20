package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class RegisterUserDto(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val emailAddress: String
)
