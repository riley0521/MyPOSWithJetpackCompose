package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.UOMEntity

@Dao
interface UOMDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(units: List<UOMEntity>)

    @Query("SELECT * FROM tbl_units WHERE businessId = :businessId")
    suspend fun getAll(businessId: Int): List<UOMEntity>

    @Query("DELETE FROM tbl_units")
    suspend fun deleteAll()
}