package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.ProductGroup

@Entity(tableName = "tbl_product_groups")
data class ProductGroupEntity(
    val name: String,
    val groupImageUrl: String,
    val businessId: Int,
    @PrimaryKey
    val productGroupId: Int = 0
) {
    fun toProductGroup(): ProductGroup {
        return ProductGroup(
            name = name,
            groupImageUrl = groupImageUrl,
            productGroupId = productGroupId
        )
    }
}
