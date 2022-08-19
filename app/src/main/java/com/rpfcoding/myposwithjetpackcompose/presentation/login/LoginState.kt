package com.rpfcoding.myposwithjetpackcompose.presentation.login

data class LoginState(
    val usernameText: String = "",
    val usernameError: String? = null,
    val passwordText: String = "",
    val passwordError: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
