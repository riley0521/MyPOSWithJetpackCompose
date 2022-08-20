package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_units")
data class UOMEntity(
    val name: String,
    val symbol: String,
    val type: Int,
    val businessId: Int,
    @PrimaryKey
    val unitOfMeasurementId: Int = 0
)
