package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val contactNo: String,
    val isDefault: Boolean
) : Parcelable
