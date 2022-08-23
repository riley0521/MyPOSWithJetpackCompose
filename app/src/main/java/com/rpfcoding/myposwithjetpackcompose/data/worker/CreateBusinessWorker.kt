package com.rpfcoding.myposwithjetpackcompose.data.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.model.Business
import com.rpfcoding.myposwithjetpackcompose.domain.repository.BusinessRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.NOTIFICATION_UPLOAD_BUSINESS_INFO_ID
import com.rpfcoding.myposwithjetpackcompose.util.Constants.UPLOADING_BUSINESS_TITLE
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_BUSINESS_IMG
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WK_BUSINESS_OBJ
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import com.rpfcoding.myposwithjetpackcompose.util.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okio.Path.Companion.toPath
import java.io.File
import javax.inject.Inject

@HiltWorker
class CreateBusinessWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var businessRepository: BusinessRepository

    override suspend fun doWork(): Result {

        val businessStr = inputData.getString(WK_BUSINESS_OBJ)
        val selectedImageUri = Uri.parse(inputData.getString(WK_BUSINESS_IMG))

        val businessObj = Gson().fromJson(businessStr, Business::class.java)

        makeStatusNotification(
            UPLOADING_BUSINESS_TITLE,
            "Uploading...",
            NOTIFICATION_UPLOAD_BUSINESS_INFO_ID,
            applicationContext
        )

        var file: File? = null

        file = try {
            selectedImageUri.toFile()
        } catch (e: Exception) {
            null
        }

        return when (val result = businessRepository.create(
            business = businessObj,
            selectedImage = file
        )) {
            is Resource.Error -> {
                makeStatusNotification(
                    UPLOADING_BUSINESS_TITLE,
                    result.message?.asString(applicationContext)
                        ?: applicationContext.getString(R.string.unknown_error),
                    NOTIFICATION_UPLOAD_BUSINESS_INFO_ID,
                    applicationContext
                )
                Result.failure()
            }
            is Resource.Success -> {
                makeStatusNotification(
                    UPLOADING_BUSINESS_TITLE,
                    "Upload successful!",
                    NOTIFICATION_UPLOAD_BUSINESS_INFO_ID,
                    applicationContext
                )
                Result.success()
            }
        }
    }
}