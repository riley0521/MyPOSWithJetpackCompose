package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class AddressDto(
    val addressId: Int,
    val userId: Int,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val contactNo: String,
    val isDefault: Boolean
)