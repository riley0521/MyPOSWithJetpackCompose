package com.rpfcoding.myposwithjetpackcompose.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefRepository: MyPreferencesRepository
) : ViewModel() {

    private val _splashEventChannel = Channel<SplashEvent>()
    val splashEventChannel = _splashEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val businessId = prefRepository.readBusinessId()
                .stateIn(viewModelScope)
                .value

            val userId = prefRepository.readUserId()
                .stateIn(viewModelScope)
                .value

            if(businessId <= 0 && userId > 0) {
                _splashEventChannel.send(
                    SplashEvent.NavigateToRegisterBusiness
                )
                return@launch
            }

            if(businessId > 0 && userId > 0) {
                _splashEventChannel.send(SplashEvent.NavigateToHome)
            } else {
                _splashEventChannel.send(SplashEvent.NavigateToLogin(null))
            }
        }
    }

    sealed class SplashEvent {
        data class NavigateToLogin(val msg: UiText?) : SplashEvent()
        object NavigateToHome : SplashEvent()
        object NavigateToRegisterBusiness : SplashEvent()
    }
}