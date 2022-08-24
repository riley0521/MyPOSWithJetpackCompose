package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.InventoryEntity

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventories: List<InventoryEntity>)

    @Query("SELECT * FROM tbl_inventories WHERE productId = :productId")
    suspend fun getAllByProductId(productId: Int): List<InventoryEntity>

    @Query("DELETE FROM tbl_inventories")
    suspend fun deleteAll()
}