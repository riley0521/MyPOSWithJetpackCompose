package com.rpfcoding.myposwithjetpackcompose.domain.model

data class AddOn(
    val name: String,
    val price: Double,
    val cost: Double,
    val stockCount: Int,
    val soldCount: Int,
    val lostCount: Int,
    val consumedCount: Int,
    val addOnId: Int = 0,
)
