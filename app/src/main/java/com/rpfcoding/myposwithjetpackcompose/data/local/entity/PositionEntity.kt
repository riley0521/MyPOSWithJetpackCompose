package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_positions")
data class PositionEntity(
    val name: String,
    val businessId: Int,
    val userId: Int,
    @PrimaryKey
    val positionId: Int = 0
)