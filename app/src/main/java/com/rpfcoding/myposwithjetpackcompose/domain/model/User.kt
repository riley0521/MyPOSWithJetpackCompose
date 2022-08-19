package com.rpfcoding.myposwithjetpackcompose.domain.model

data class User(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val profileImageUrl: String,
    val emailAddress: String,
    val isBusinessOwner: Boolean,
    val isActive: Boolean,
    val business: Business? = null,
    val position: Position? = null,
    val addresses: List<Address> = emptyList(),
    val modules: List<Module> = emptyList()
)
