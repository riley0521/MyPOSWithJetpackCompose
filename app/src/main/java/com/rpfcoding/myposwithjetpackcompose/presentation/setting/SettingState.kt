package com.rpfcoding.myposwithjetpackcompose.presentation.setting

import com.rpfcoding.myposwithjetpackcompose.domain.model.User

data class SettingState(
    val user: User = User(),
    val error: String? = null,
    val isLoading: Boolean = false
)
