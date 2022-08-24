package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val description: String,
    val unit: String,
    val symbol: String,
    val currencyId: Int = 0,
): Parcelable
