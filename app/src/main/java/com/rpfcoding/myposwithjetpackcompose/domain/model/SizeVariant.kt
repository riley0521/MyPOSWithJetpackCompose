package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SizeVariant(
    val name: String,
    val sizeVariantId: Int = 0
) : Parcelable
