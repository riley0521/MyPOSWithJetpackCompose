package com.rpfcoding.myposwithjetpackcompose.presentation.register_user

import com.rpfcoding.myposwithjetpackcompose.util.UiText

data class RegisterUserState(
    val usernameText: String = "",
    val usernameError: UiText? = null,
    val passwordText: String = "",
    val passwordError: UiText? = null,
    val confirmPassText: String = "",
    val confirmPassError: UiText? = null,
    val firstNameText: String = "",
    val firstNameError: UiText? = null,
    val middleNameText: String = "",
    val middleNameError: UiText? = null,
    val lastNameText: String = "",
    val lastNameError: UiText? = null,
    val emailText: String = "",
    val emailError: UiText? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
