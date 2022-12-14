package com.rpfcoding.myposwithjetpackcompose.domain.repository

import androidx.paging.PagingData
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductGroupEntity
import com.rpfcoding.myposwithjetpackcompose.domain.model.Product
import com.rpfcoding.myposwithjetpackcompose.domain.model.ProductGroup
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProductRepository {

    suspend fun fetchAllProductGroup(): Resource<Unit>

    suspend fun fetchProducts(): Resource<Unit>

    fun getPaginatedProductGroups(businessId: Int): Flow<PagingData<ProductGroupEntity>>

    fun getPaginatedProducts(groupId: Int): Flow<PagingData<ProductEntity>>

    suspend fun createProductGroup(
        productGroup: ProductGroup,
        imageFile: File?
    ): Resource<Pair<Int, String>>

    suspend fun createProduct(
        groupId: Int,
        product: Product,
        imageFile: File?
    ): Resource<Pair<Int, String>>
}