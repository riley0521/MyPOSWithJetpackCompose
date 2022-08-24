package com.rpfcoding.myposwithjetpackcompose.presentation.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
   private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(SettingState())
        private set

    init {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            state = state.copy(
                user = userRepository.getUserInfo() ?: User()
            )

            state = state.copy(isLoading = false)
        }
    }
}