package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ModuleEntity

@Dao
interface ModuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(modules: List<ModuleEntity>)

    @Query("SELECT * FROM tbl_modules WHERE userId = :userId")
    suspend fun getByUserId(userId: Int): List<ModuleEntity>

    @Query("DELETE FROM tbl_modules")
    suspend fun deleteAll()
}