package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.BusinessEntity

@Dao
interface BusinessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(business: BusinessEntity)

    @Query("SELECT * FROM tbl_businesses WHERE userId = :userId")
    suspend fun getByUserId(userId: Int): BusinessEntity?

    @Query("DELETE FROM tbl_businesses")
    suspend fun deleteAll()
}