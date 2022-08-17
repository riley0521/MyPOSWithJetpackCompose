package com.rpfcoding.myposwithjetpackcompose.domain.model

data class User(
    val businessId: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val profileImageUrl: String,
    val emailAddress: String,
    val isBusinessOwner: Boolean,
    val isActive: Boolean,
    val positionId: Int? = null,
    val userId: Int = 0,
)
