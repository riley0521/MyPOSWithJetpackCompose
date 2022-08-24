package com.rpfcoding.myposwithjetpackcompose.presentation.profile

import com.rpfcoding.myposwithjetpackcompose.domain.model.User

data class ProfileState(
    val user: User = User(),
    val error: String? = null,
    val isLoading: Boolean = false
)
