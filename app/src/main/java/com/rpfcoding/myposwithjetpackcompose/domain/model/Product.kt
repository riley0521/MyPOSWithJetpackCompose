package com.rpfcoding.myposwithjetpackcompose.domain.model

data class Product(
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
    val productId: Int = 0,
    val inventories: List<Inventory> = emptyList(),
    val addOns: List<AddOn> = emptyList()
)
