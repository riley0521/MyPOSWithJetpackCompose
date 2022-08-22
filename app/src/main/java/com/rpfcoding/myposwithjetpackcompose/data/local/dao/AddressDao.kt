package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.AddressEntity

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(addresses: List<AddressEntity>)

    @Query("SELECT * FROM tbl_addresses WHERE userId = :userId")
    suspend fun getByUserId(userId: Int): List<AddressEntity>

    @Query("DELETE FROM tbl_addresses")
    suspend fun deleteAll()
}