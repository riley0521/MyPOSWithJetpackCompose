package com.rpfcoding.myposwithjetpackcompose.presentation.menu

import com.rpfcoding.myposwithjetpackcompose.domain.model.Module

data class MenuState(
    val modules: List<Module> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
