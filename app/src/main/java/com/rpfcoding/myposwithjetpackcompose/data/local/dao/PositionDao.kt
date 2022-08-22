package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.PositionEntity

@Dao
interface PositionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(position: PositionEntity)

    @Query("SELECT * FROM tbl_positions WHERE userId = :userId")
    suspend fun getByUserId(userId: Int): List<PositionEntity>

    @Query("DELETE FROM tbl_positions")
    suspend fun deleteAll()
}