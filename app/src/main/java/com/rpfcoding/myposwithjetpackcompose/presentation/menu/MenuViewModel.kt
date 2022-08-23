package com.rpfcoding.myposwithjetpackcompose.presentation.menu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myposwithjetpackcompose.domain.model.Module
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ModuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository
) : ViewModel() {

    var state by mutableStateOf(MenuState())
        private set

    init {
        fetchModules()
    }

    private fun fetchModules() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            state = state.copy(
                modules = moduleRepository.getAll()
            )

            state = state.copy(isLoading = false)
        }
    }
}