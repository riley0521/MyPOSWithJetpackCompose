package com.rpfcoding.myposwithjetpackcompose.data.worker

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.local.MyDatabase
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ProductGroupEntity
import com.rpfcoding.myposwithjetpackcompose.domain.model.ProductGroup
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ProductRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID
import com.rpfcoding.myposwithjetpackcompose.util.Constants.UPLOADING_PRODUCT_GROUP_TITLE
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_PRODUCT_GROUP_OBJ
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_PRODUCT_GROUP_URI
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

@HiltWorker
class CreateProductGroupWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var prefRepository: MyPreferencesRepository

    @Inject
    lateinit var db: MyDatabase

    override suspend fun doWork(): Result {

        val productGroupStr = inputData.getString(WK_PRODUCT_GROUP_OBJ)
        val imageUri = Uri.parse(inputData.getString(WK_PRODUCT_GROUP_URI))

        val productGroupObj = Gson().fromJson(productGroupStr, ProductGroup::class.java)

        var file: File? = null

        file = try {
            imageUri.toFile()
        } catch (e: Exception) {
            null
        }

        makeStatusNotification(
            UPLOADING_PRODUCT_GROUP_TITLE,
            "Uploading...",
            NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID,
            applicationContext,
            true
        )

        return when (val result = productRepository.createProductGroup(productGroupObj, file)) {
            is Resource.Error -> {
                makeStatusNotification(
                    UPLOADING_PRODUCT_GROUP_TITLE,
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
                    UPLOADING_PRODUCT_GROUP_TITLE,
                    applicationContext.getString(R.string.success),
                    NOTIFICATION_UPLOAD_PRODUCT_GROUP_ID,
                    applicationContext,
                    false
                )

                val businessId =
                    prefRepository.readBusinessId().stateIn(CoroutineScope(coroutineContext)).value
                db.productGroupDao().insert(
                    listOf(
                        ProductGroupEntity(
                            name = productGroupObj.name,
                            groupImageUrl = result.data?.second ?: "",
                            businessId = businessId,
                            productGroupId = result.data?.first ?: 0
                        )
                    )
                )
                Result.success()
            }
        }
    }
}