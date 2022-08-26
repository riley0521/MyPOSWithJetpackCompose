package com.rpfcoding.myposwithjetpackcompose.data.worker

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.local.MyDatabase
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductEntity
import com.rpfcoding.myposwithjetpackcompose.domain.model.Product
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ProductRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID
import com.rpfcoding.myposwithjetpackcompose.util.Constants.UPLOADING_PRODUCT_TITLE
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_PRODUCT_GROUP_ID
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_PRODUCT_ID
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_PRODUCT_OBJ
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_PRODUCT_URI
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import javax.inject.Inject

@HiltWorker
class CreateProductWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var db: MyDatabase

    override suspend fun doWork(): Result {

        val groupId = inputData.getInt(WK_PRODUCT_GROUP_ID, -1)

        if (groupId <= 0) {
            return Result.failure()
        }

        val productStr = inputData.getString(WK_PRODUCT_OBJ)
        val imageUri = Uri.parse(inputData.getString(WK_PRODUCT_URI))

        val productObj = Gson().fromJson(productStr, Product::class.java)

        var file: File? = null

        file = try {
            imageUri.toFile()
        } catch (e: Exception) {
            null
        }

        makeStatusNotification(
            UPLOADING_PRODUCT_TITLE,
            "Uploading...",
            NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID,
            applicationContext,
            true
        )

        return when (val result = productRepository.createProduct(
            groupId,
            productObj,
            file
        )) {
            is Resource.Error -> {
                makeStatusNotification(
                    UPLOADING_PRODUCT_TITLE,
                    result.message?.asString(applicationContext)
                        ?: applicationContext.getString(R.string.unknown_error),
                    NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID,
                    applicationContext,
                    false
                )
                Result.failure()
            }
            is Resource.Success -> {
                makeStatusNotification(
                    UPLOADING_PRODUCT_TITLE,
                    applicationContext.getString(R.string.success),
                    NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID,
                    applicationContext,
                    false
                )
                db.productDao().insert(
                    listOf(
                        ProductEntity(
                            name = productObj.name,
                            basePrice = productObj.basePrice,
                            baseCost = productObj.baseCost,
                            type = productObj.type,
                            description = productObj.description,
                            productImageUrl = result.data?.second ?: "",
                            numberOfSold = 0,
                            sumOfRate = 0,
                            numberOfReviews = 0,
                            isActive = true,
                            productGroupId = groupId,
                            productId = result.data?.first ?: 0
                        )
                    )
                )
                Result.success(
                    workDataOf(
                        WK_PRODUCT_ID to result.data?.first,
                        WK_PRODUCT_OBJ to productStr
                    )
                )
            }
        }
    }
}