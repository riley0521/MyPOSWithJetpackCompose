package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_addresses")
data class AddressEntity(
    val userId: Int,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val contactNo: String,
    val isDefault: Boolean,
    @PrimaryKey
    val addressId: Int = 0
)
