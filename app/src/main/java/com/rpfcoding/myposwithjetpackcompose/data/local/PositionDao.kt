package com.rpfcoding.myposwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.PositionEntity

@Dao
interface PositionDao {

    @Insert
    suspend fun insert(position: PositionEntity)

    @Query("SELECT * FROM tbl_positions WHERE positionId = :positionId")
    suspend fun getById(positionId: Int): PositionEntity?

    @Query("DELETE FROM tbl_positions")
    suspend fun deleteAll(): Boolean
}