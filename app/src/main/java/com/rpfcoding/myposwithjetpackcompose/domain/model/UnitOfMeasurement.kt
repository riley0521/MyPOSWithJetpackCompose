package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnitOfMeasurement(
    val name: String,
    val symbol: String,
    val type: Int,
    val unitOfMeasurementId: Int = 0
) : Parcelable
