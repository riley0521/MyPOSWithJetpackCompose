package com.rpfcoding.myposwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.AddressEntity

@Dao
interface AddressDao {

    @Insert
    suspend fun insert(address: AddressEntity)

    @Query("SELECT * FROM tbl_addresses WHERE addressId = :addressId")
    suspend fun getById(addressId: Int): AddressEntity?

    @Query("DELETE FROM tbl_addresses")
    suspend fun deleteAll(): Boolean
}