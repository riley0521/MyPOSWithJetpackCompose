package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductGroupEntity

@Dao
interface ProductGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(groups: List<ProductGroupEntity>)

    @Query("SELECT * FROM tbl_product_groups WHERE businessId = :businessId")
    suspend fun getAllByBusinessId(businessId: Int): List<ProductGroupEntity>

    @Query("SELECT * FROM tbl_product_groups WHERE businessId = :businessId")
    fun getByBusinessIdPaginated(businessId: Int): PagingSource<Int, ProductGroupEntity>

    @Query("DELETE FROM tbl_product_groups")
    suspend fun deleteAll()
}