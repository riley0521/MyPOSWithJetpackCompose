package com.rpfcoding.myposwithjetpackcompose.presentation.edit_profile

import com.rpfcoding.myposwithjetpackcompose.util.UiText

data class EditProfileState(
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
