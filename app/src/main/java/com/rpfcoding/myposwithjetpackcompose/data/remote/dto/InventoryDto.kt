package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class InventoryDto(
    val inventoryId: Int,
    val productId: Int,
    val unitOfMeasurementId: Int,
    val sizeVariantId: Int,
    val additionalPrice: Double,
    val additionalCost: Double,
    val code: String,
    val stockCount: Int,
    val committedCount: Int,
    val soldCount: Int,
    val returnedCount: Int,
    val lostCount: Int,
    val consumedCount: Int
)
