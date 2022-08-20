package com.rpfcoding.myposwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.SizeTypeEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.SizeVariantEntity

@Dao
interface SizeVariantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sizeVariants: List<SizeVariantEntity>)

    @Query("SELECT * FROM tbl_size_variants WHERE sizeTypeId = :sizeTypeId")
    suspend fun getAll(sizeTypeId: Int): List<SizeVariantEntity>

    @Query("DELETE FROM tbl_size_variants")
    suspend fun deleteAll()
}
