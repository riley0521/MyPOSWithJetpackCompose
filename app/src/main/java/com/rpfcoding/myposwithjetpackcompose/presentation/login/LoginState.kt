package com.rpfcoding.myposwithjetpackcompose.presentation.login

import com.rpfcoding.myposwithjetpackcompose.util.UiText

data class LoginState(
    val usernameText: String = "",
    val usernameError: UiText? = null,
    val passwordText: String = "",
    val passwordError: UiText? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
