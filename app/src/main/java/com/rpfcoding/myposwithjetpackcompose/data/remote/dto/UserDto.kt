package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class UserDto(
    val userId: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val profileImageUrl: String,
    val emailAddress: String,
    val isBusinessOwner: Boolean,
    val isActive: Boolean,
    val business: BusinessDto? = null,
    val position: PositionDto? = null,
    val addresses: List<AddressDto>,
    val modules: List<ModuleDto>,
)
