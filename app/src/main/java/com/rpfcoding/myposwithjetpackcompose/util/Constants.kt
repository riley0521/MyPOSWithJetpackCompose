package com.rpfcoding.myposwithjetpackcompose.util

object Constants {

    const val PREFERENCES_NAME = "my_preferences"
    const val PREF_USER_ID_KEY = "pref_user_id_key"
    const val PREF_BUSINESS_ID_KEY = "pref_business_id_key"
    const val PREF_TOKEN_KEY = "pref_token_key"

    const val MIN_USERNAME_LENGTH = 4
    const val MIN_PASSWORD_LENGTH = 8

    const val BASE_URL = "http://10.0.2.2:5009/api/v1/"

    val countries = listOf(
        "Philippines",
        "Canada",
        "Vietnam"
    )

    fun getAuth(token: String): String = "Bearer $token"
}

fun isLetters(value: String): Boolean {
    return value.all { it.isLetter() || it == '-' || it.isWhitespace() }
}