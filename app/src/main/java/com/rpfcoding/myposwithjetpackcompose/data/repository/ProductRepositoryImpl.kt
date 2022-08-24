package com.rpfcoding.myposwithjetpackcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.local.MyDatabase
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.AddOnEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.InventoryEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductGroupEntity
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiProductEndpoints
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ProductRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import com.rpfcoding.myposwithjetpackcompose.util.getImageUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiProductEndpoints,
    private val prefRepository: MyPreferencesRepository,
    private val db: MyDatabase
) : ProductRepository {

    override suspend fun fetchAllProductGroup(): Resource<Unit> {
        return try {
            val businessId = prefRepository.readBusinessId().stateIn(CoroutineScope(coroutineContext)).value

            val result = api.getAllProductGroup(businessId)

            val dao = db.productGroupDao()

            dao.insert(result.map {
                ProductGroupEntity(
                    name = it.name,
                    groupImageUrl = it.groupImageUrl,
                    businessId = businessId,
                    productGroupId = it.productGroupId
                )
            })

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }

    override suspend fun fetchProducts(): Resource<Unit> {
        return try {
            val groupDao = db.productGroupDao()
            val productDao = db.productDao()
            val inventoryDao = db.inventoryDao()
            val addOnDao = db.addOnDao()

            val businessId = prefRepository.readBusinessId().stateIn(CoroutineScope(coroutineContext)).value

            val groups = groupDao.getAllByBusinessId(businessId)

            groups.forEach { group ->

                var page = 1

                while(true) {
                    val result = api.getSomeProduct(group.productGroupId, page)
                    if(result.result.isEmpty()) break

                    productDao.insert(result.result.map {
                        ProductEntity(
                            name = it.name,
                            baseCost = it.baseCost,
                            basePrice = it.basePrice,
                            type = it.type,
                            description = it.description,
                            productImageUrl = getImageUrl(it.productImageUrl),
                            numberOfSold = it.numberOfSold,
                            sumOfRate = it.sumOfRate,
                            numberOfReviews = it.numberOfReviews,
                            isActive = it.isActive,
                            productGroupId = it.productGroupId,
                            productId = it.productId
                        )
                    })

                    result.result.forEach { product ->
                        inventoryDao.insert(
                            product.inventories.map {
                                InventoryEntity(
                                    additionalPrice = it.additionalPrice,
                                    additionalCost = it.additionalCost,
                                    code = it.code,
                                    stockCount = it.stockCount,
                                    committedCount = it.committedCount,
                                    soldCount = it.soldCount,
                                    returnedCount = it.returnedCount,
                                    lostCount = it.lostCount,
                                    consumedCount = it.consumedCount,
                                    productId = it.productId,
                                    unitOfMeasurementId = it.unitOfMeasurementId,
                                    sizeVariantId = it.sizeVariantId,
                                    inventoryId = it.inventoryId
                                )
                            }
                        )

                        addOnDao.insert(
                            product.addOns.map {
                                AddOnEntity(
                                    name = it.name,
                                    price = it.price,
                                    cost = it.cost,
                                    soldCount = it.soldCount,
                                    stockCount = it.stockCount,
                                    lostCount = it.lostCount,
                                    consumedCount = it.consumedCount,
                                    productId = it.productId,
                                    addOnId = it.addOnId
                                )
                            }
                        )
                    }

                    page++
                }
            }

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }

    override fun getPaginatedProductGroups(businessId: Int): Flow<PagingData<ProductGroupEntity>> {
        val productGroupDao = db.productGroupDao()

        val pagingSource = { productGroupDao.getByBusinessIdPaginated(businessId) }
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 30
            ),
            pagingSourceFactory = pagingSource
        ).flow
    }

    override fun getPaginatedProducts(groupId: Int): Flow<PagingData<ProductEntity>> {
        val productDao = db.productDao()

        val pagingSource = { productDao.getByGroupIdPaginated(groupId) }
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 30
            ),
            pagingSourceFactory = pagingSource
        ).flow
    }
}