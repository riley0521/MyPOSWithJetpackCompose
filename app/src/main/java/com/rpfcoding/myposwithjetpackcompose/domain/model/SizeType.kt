package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SizeType(
    val name: String,
    val sizeTypeId: Int = 0,
    val sizeVariants: List<SizeVariant>
) : Parcelable
