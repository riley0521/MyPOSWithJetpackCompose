package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.SizeTypeEntity

@Dao
interface SizeTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sizeTypes: List<SizeTypeEntity>)

    @Query("SELECT * FROM tbl_size_types WHERE businessId = :businessId")
    suspend fun getAll(businessId: Int): List<SizeTypeEntity>

    @Query("DELETE FROM tbl_size_types")
    suspend fun deleteAll()
}