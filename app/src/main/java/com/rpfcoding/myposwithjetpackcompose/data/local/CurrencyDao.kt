package com.rpfcoding.myposwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM tbl_currencies WHERE businessId = :businessId")
    suspend fun getByUserId(businessId: Int): List<CurrencyEntity>

    @Query("DELETE FROM tbl_currencies")
    suspend fun deleteAll()
}