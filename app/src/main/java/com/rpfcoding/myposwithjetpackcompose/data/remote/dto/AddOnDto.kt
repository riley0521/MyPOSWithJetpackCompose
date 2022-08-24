package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class AddOnDto(
    val addOnId: Int,
    val productId: Int,
    val name: String,
    val price: Double,
    val cost: Double,
    val stockCount: Int,
    val soldCount: Int,
    val lostCount: Int,
    val consumedCount: Int
)
