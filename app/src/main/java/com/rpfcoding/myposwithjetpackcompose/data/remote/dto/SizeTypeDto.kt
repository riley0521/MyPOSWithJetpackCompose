package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class SizeTypeDto(
    val name: String,
    val sizeTypeId: Int,
    val sizeVariants: List<SizeVariantDto>
)
