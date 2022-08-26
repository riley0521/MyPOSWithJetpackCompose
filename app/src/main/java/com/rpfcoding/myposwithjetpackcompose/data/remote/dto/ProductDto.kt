package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class ProductDto(
    val productGroupId: Int,
    val name: String,
    val basePrice: Double,
    val baseCost: Double,
    val type: Int,
    val description: String,
    val productImageUrl: String,
    val numberOfSold: Int = 0,
    val sumOfRate: Int = 0,
    val numberOfReviews: Int = 0,
    val isActive: Boolean = true,
    val productId: Int = 0,
    val inventories: List<InventoryDto> = emptyList(),
    val addOns: List<AddOnDto> = emptyList()
)
