package com.rpfcoding.myposwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ModuleEntity

@Dao
interface ModuleDao {

    @Insert
    suspend fun insert(module: ModuleEntity)

    @Query("SELECT * FROM tbl_modules WHERE moduleId = :moduleId")
    suspend fun getById(moduleId: Int): ModuleEntity?

    @Query("DELETE FROM tbl_modules")
    suspend fun deleteAll(): Boolean
}