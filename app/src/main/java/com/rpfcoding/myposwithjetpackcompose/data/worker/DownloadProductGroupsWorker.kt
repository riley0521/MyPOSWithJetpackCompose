package com.rpfcoding.myposwithjetpackcompose.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ProductRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.DOWNLOAD_PRODUCT_GROUPS_TITLE
import com.rpfcoding.myposwithjetpackcompose.util.Constants.NOTIFICATION_DOWNLOAD_PRODUCT_ID
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class DownloadProductGroupsWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
): CoroutineWorker(ctx, params) {

    @Inject
    lateinit var productRepository: ProductRepository

    override suspend fun doWork(): Result {

        makeStatusNotification(
            DOWNLOAD_PRODUCT_GROUPS_TITLE,
            "Downloading...",
            NOTIFICATION_DOWNLOAD_PRODUCT_ID,
            applicationContext,
            true
        )

        return when(val result = productRepository.fetchAllProductGroup()) {
            is Resource.Error -> {
                makeStatusNotification(
                    DOWNLOAD_PRODUCT_GROUPS_TITLE,
                    result.message?.asString(applicationContext) ?: applicationContext.getString(R.string.unknown_error),
                    NOTIFICATION_DOWNLOAD_PRODUCT_ID,
                    applicationContext,
                    false
                )
                Result.retry()
            }
            is Resource.Success -> {
                makeStatusNotification(
                    DOWNLOAD_PRODUCT_GROUPS_TITLE,
                    "Downloading successful!",
                    NOTIFICATION_DOWNLOAD_PRODUCT_ID,
                    applicationContext,
                    false
                )
                Result.success()
            }
        }
    }
}