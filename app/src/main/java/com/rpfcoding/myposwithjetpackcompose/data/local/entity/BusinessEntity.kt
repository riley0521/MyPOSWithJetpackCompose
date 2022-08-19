package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_businesses")
data class BusinessEntity(
    val name: String,
    val businessLogoUrl: String,
    val facebookUrl: String,
    val instagramUrl: String,
    val twitterUrl: String,
    val landlineNo: String,
    val email: String,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val userId: Int,
    @PrimaryKey
    val businessId: Int = 0,
)
