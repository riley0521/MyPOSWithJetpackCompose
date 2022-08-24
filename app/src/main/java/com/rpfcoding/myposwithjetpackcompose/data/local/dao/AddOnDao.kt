package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.AddOnEntity

@Dao
interface AddOnDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(addOns: List<AddOnEntity>)

    @Query("SELECT * FROM tbl_add_ons WHERE productId = :productId")
    suspend fun getAllByProductId(productId: Int): List<AddOnEntity>

    @Query("DELETE FROM tbl_add_ons")
    suspend fun deleteAll()
}