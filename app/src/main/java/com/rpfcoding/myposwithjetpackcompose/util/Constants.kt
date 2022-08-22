package com.rpfcoding.myposwithjetpackcompose.util

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.util.Constants.POS_CHANNEL_NAME

object Constants {

    const val PREFERENCES_NAME = "my_preferences"
    const val PREF_USER_ID_KEY = "pref_user_id_key"
    const val PREF_BUSINESS_ID_KEY = "pref_business_id_key"
    const val PREF_TOKEN_KEY = "pref_token_key"
    const val PREF_IS_INFO_DOWNLOADED_KEY = "pref_is_info_downloaded_key"

    const val MIN_USERNAME_LENGTH = 4
    const val MIN_PASSWORD_LENGTH = 8

    const val POS_CHANNEL_NAME = "pos_channel_name"

    const val NOTIFICATION_DOWNLOAD_USER_INFO_ID = 241
    const val NOTIFICATION_UPLOAD_BUSINESS_INFO_ID = 242

    const val DOWNLOAD_USER_INFORMATION_TITLE = "Download User Information"
    const val UPLOADING_BUSINESS_TITLE = "Upload Business Information"

    const val WORKER_DOWNLOAD_USER_INFORMATION = "worker_download_user_information"
    const val WORKER_CREATE_BUSINESS = "worker_create_business"

    const val WK_BUSINESS_OBJ = "wk_business_obj"
    const val WK_BUSINESS_IMG = "wk_business_img"

    const val BASE_URL = "http://10.0.2.2:5009/api/v1/"

    val countries = listOf(
        "Philippines",
        "Canada",
        "Vietnam"
    )

}

fun getAuth(token: String): String = "Bearer $token"

fun isLetters(value: String): Boolean {
    return value.all { it.isLetter() || it == '-' || it.isWhitespace() }
}

fun makeStatusNotification(title: String, message: String, notificationId: Int, ctx: Context) {
    // Create the notification
    val builder = NotificationCompat.Builder(ctx, POS_CHANNEL_NAME)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(ctx).notify(notificationId, builder.build())
}