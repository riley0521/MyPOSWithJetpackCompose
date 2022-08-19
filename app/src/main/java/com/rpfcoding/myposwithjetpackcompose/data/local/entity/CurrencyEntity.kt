package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_currencies")
data class CurrencyEntity(
    val description: String,
    val unit: String,
    val symbol: String,
    val businessId: Int,
    @PrimaryKey
    val currencyId: Int = 0,
)
