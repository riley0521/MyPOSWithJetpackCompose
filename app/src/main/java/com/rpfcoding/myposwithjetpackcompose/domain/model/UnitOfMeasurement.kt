package com.rpfcoding.myposwithjetpackcompose.domain.model

data class UnitOfMeasurement(
    val name: String,
    val symbol: String,
    val type: Int,
    val unitOfMeasurementId: Int = 0
)
