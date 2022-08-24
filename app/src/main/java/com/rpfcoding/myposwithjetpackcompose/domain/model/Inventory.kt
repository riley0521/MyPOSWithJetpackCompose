package com.rpfcoding.myposwithjetpackcompose.domain.model

import androidx.room.PrimaryKey

data class Inventory(
    val additionalPrice: Double,
    val additionalCost: Double,
    val code: String,
    val stockCount: Int,
    val committedCount: Int,
    val soldCount: Int,
    val returnedCount: Int,
    val lostCount: Int,
    val consumedCount: Int,
    val uom: UnitOfMeasurement?,
    val sizeVariant: SizeVariant?,
    @PrimaryKey
    val inventoryId: Int = 0,
)
