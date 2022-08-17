package com.rpfcoding.myposwithjetpackcompose.domain.model

data class Address(
    val userId: Int,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val contactNo: String,
    val isDefault: Boolean,
    val addressId: Int = 0
)
