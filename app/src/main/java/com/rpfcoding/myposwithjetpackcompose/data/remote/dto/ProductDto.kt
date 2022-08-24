package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class ProductDto(
    val productId: Int,
    val productGroupId: Int,
    val name: String,
    val basePrice: Double,
    val baseCost: Double,
    val type: Int,
    val description: String,
    val productImageUrl: String,
    val numberOfSold: Int,
    val sumOfRate: Int,
    val numberOfReviews: Int,
    val isActive: Boolean,
    val inventories: List<InventoryDto>,
    val addOns: List<AddOnDto>
)
