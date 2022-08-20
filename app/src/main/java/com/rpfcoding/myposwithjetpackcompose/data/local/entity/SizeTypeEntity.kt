package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_size_types")
data class SizeTypeEntity(
    val name: String,
    val businessId: Int,
    @PrimaryKey
    val sizeTypeId: Int = 0,
)
