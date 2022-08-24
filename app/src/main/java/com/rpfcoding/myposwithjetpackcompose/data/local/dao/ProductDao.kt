package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>)

    @Query("SELECT * FROM tbl_products WHERE productGroupId = :productGroupId")
    fun getByGroupIdPaginated(productGroupId: Int): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM tbl_products")
    suspend fun deleteAll()
}