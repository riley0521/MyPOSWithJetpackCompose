package com.rpfcoding.myposwithjetpackcompose.util

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.model.Module
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MenuItem
import com.rpfcoding.myposwithjetpackcompose.presentation.menu.MenuData
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

    val moduleItems = listOf(
        MenuData(
            2,
            title = "Add Ons",
            icon = Icons.Filled.Add,
        ),
        MenuData(
            5,
            title = "Currencies",
            icon = Icons.Filled.Money,
        ),
        MenuData(
            6,
            title = "Expenses",
            icon = Icons.Filled.Outbound,
        ),
        MenuData(
            7,
            title = "Inventories",
            icon = Icons.Filled.Inventory,
        ),
        MenuData(
            8,
            title = "Positions",
            icon = Icons.Filled.Person,
        ),
        MenuData(
            9,
            title = "Products",
            icon = Icons.Filled.Add,
        ),
        MenuData(
            10,
            title = "Product Groups",
            icon = Icons.Filled.Group,
        ),
        MenuData(
            11,
            title = "Sales",
            icon = Icons.Filled.Store,
        ),
        MenuData(
            13,
            title = "Size Types",
            icon = Icons.Filled.Inventory2,
        ),
        MenuData(
            15,
            title = "Unit of Measurements",
            icon = Icons.Filled.FormatSize,
        ),
        MenuData(
            16,
            title = "Manage Users",
            icon = Icons.Filled.Person,
        )
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