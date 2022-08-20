package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_size_variants")
data class SizeVariantEntity(
    val name: String,
    val sizeTypeId: Int,
    @PrimaryKey
    val sizeVariantId: Int = 0
)
