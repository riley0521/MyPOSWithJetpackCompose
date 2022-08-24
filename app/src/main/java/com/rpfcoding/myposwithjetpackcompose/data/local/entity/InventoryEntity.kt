package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.Inventory
import com.rpfcoding.myposwithjetpackcompose.domain.model.SizeVariant
import com.rpfcoding.myposwithjetpackcompose.domain.model.UnitOfMeasurement

@Entity(tableName = "tbl_inventories")
data class InventoryEntity(
    val additionalPrice: Double,
    val additionalCost: Double,
    val code: String,
    val stockCount: Int,
    val committedCount: Int,
    val soldCount: Int,
    val returnedCount: Int,
    val lostCount: Int,
    val consumedCount: Int,
    val productId: Int,
    val unitOfMeasurementId: Int,
    val sizeVariantId: Int,
    @PrimaryKey
    val inventoryId: Int = 0,
) {
    fun toInventory(
        uom: UnitOfMeasurement?,
        sizeVariant: SizeVariant?
    ): Inventory {
        return Inventory(
            additionalCost = additionalCost,
            additionalPrice = additionalPrice,
            code = code,
            stockCount = stockCount,
            committedCount = committedCount,
            soldCount = soldCount,
            returnedCount = returnedCount,
            lostCount = lostCount,
            consumedCount = consumedCount,
            uom = uom,
            sizeVariant = sizeVariant,
            inventoryId = inventoryId
        )
    }
}
