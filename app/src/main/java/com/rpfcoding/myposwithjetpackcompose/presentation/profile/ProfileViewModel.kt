package com.rpfcoding.myposwithjetpackcompose.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by savedStateHandle.saveable { mutableStateOf(ProfileState()) }
        private set

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            state = state.copy(
                user = userRepository.getUserInfo() ?: User()
            )

            state = state.copy(isLoading = false)
        }
    }
}