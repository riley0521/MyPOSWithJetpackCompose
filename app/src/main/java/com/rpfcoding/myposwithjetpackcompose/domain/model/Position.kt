package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    val name: String,
): Parcelable
