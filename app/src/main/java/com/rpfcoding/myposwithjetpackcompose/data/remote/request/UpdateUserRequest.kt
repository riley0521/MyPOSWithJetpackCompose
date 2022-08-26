package com.rpfcoding.myposwithjetpackcompose.data.remote.request

data class UpdateUserRequest(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val emailAddress: String
)
