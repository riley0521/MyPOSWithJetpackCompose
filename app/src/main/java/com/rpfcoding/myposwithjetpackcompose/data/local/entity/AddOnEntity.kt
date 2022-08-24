package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.AddOn

@Entity(tableName = "tbl_add_ons")
data class AddOnEntity(
    val name: String,
    val price: Double,
    val cost: Double,
    val stockCount: Int,
    val soldCount: Int,
    val lostCount: Int,
    val consumedCount: Int,
    val productId: Int,
    @PrimaryKey
    val addOnId: Int = 0,
) {
    fun toAddOn(): AddOn {
        return AddOn(
            name = name,
            price = price,
            cost = cost,
            stockCount = stockCount,
            soldCount = soldCount,
            lostCount = lostCount,
            consumedCount = consumedCount,
            addOnId = addOnId
        )
    }
}
