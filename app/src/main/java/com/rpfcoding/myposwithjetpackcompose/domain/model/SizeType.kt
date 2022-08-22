package com.rpfcoding.myposwithjetpackcompose.domain.model

data class SizeType(
    val name: String,
    val sizeTypeId: Int = 0,
    val sizeVariants: List<SizeVariant>
)
