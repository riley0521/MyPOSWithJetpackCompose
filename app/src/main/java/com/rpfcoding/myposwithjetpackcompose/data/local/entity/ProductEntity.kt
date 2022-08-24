package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.AddOn
import com.rpfcoding.myposwithjetpackcompose.domain.model.Inventory
import com.rpfcoding.myposwithjetpackcompose.domain.model.Product

@Entity(tableName = "tbl_products")
data class ProductEntity(
    val name: String,
    val basePrice: Double,
    val baseCost: Double,
    val type: Int,
    val description: String,
    val productImageUrl: String,
    val numberOfSold: Int,
    val sumOfRate: Int,
    val numberOfReviews: Int,
    val isActive: Boolean,
    val productGroupId: Int,
    @PrimaryKey
    val productId: Int = 0,
) {
    fun toProduct(
        inventories: List<Inventory>,
        addOns: List<AddOn>
    ): Product {
        return Product(
            name = name,
            basePrice = basePrice,
            baseCost = baseCost,
            type = type,
            description = description,
            productImageUrl = productImageUrl,
            numberOfSold = numberOfSold,
            sumOfRate = sumOfRate,
            numberOfReviews = numberOfReviews,
            isActive = isActive,
            productId = productId,
            inventories = inventories,
            addOns = addOns
        )
    }
}
