package com.rpfcoding.myposwithjetpackcompose.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants
import com.rpfcoding.myposwithjetpackcompose.util.Constants.DOWNLOAD_USER_INFORMATION_TITLE
import com.rpfcoding.myposwithjetpackcompose.util.Constants.NOTIFICATION_DOWNLOAD_USER_INFO_ID
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltWorker
class DownloadUserInfoWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var prefRepository: MyPreferencesRepository

    override suspend fun doWork(): Result {

        val token = prefRepository.readToken().stateIn(CoroutineScope(coroutineContext)).value
        val isInfoDownloaded =
            prefRepository.readIsInfoDownloaded().stateIn(CoroutineScope(coroutineContext)).value

        if(!isInfoDownloaded) {
            makeStatusNotification(
                DOWNLOAD_USER_INFORMATION_TITLE,
                "Downloading...",
                NOTIFICATION_DOWNLOAD_USER_INFO_ID,
                ctx = applicationContext
            )

            return when (val result = authRepository.downloadInfo(token)) {
                is Resource.Error -> {
                    makeStatusNotification(
                        DOWNLOAD_USER_INFORMATION_TITLE,
                        result.message?.asString(applicationContext) ?: "",
                        NOTIFICATION_DOWNLOAD_USER_INFO_ID,
                        ctx = applicationContext
                    )
                    Result.failure()
                }
                is Resource.Success -> {
                    makeStatusNotification(
                        DOWNLOAD_USER_INFORMATION_TITLE,
                        "Download successful!",
                        NOTIFICATION_DOWNLOAD_USER_INFO_ID,
                        ctx = applicationContext
                    )
                    Result.success()
                }
            }
        }

        return Result.success()
    }
}