package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String = "Guest",
    val middleName: String = "",
    val lastName: String = "Guest",
    val profileImageUrl: String = "",
    val emailAddress: String = "example@test.com",
    val isBusinessOwner: Boolean = false,
    val isActive: Boolean = true,
    val business: Business? = null,
    val position: Position? = null,
    val addresses: List<Address> = emptyList(),
    val modules: List<Module> = emptyList()
) : Parcelable
