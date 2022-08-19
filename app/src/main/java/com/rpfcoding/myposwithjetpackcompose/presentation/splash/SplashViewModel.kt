package com.rpfcoding.myposwithjetpackcompose.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefRepository: MyPreferencesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _splashEventChannel = Channel<SplashEvent>()
    val splashEventChannel = _splashEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val token = prefRepository.readToken()
                .stateIn(viewModelScope)
                .value

            if(token.isNotBlank()) {
                when (val result = authRepository.isAuthenticated(token)) {
                    is Resource.Error -> {
                        _splashEventChannel.send(
                            SplashEvent.NavigateToLogin(
                                result.message
                            )
                        )
                    }
                    is Resource.Success -> {
                        _splashEventChannel.send(SplashEvent.NavigateToHome)
                    }
                }
            } else {
                _splashEventChannel.send(SplashEvent.NavigateToLogin(null))
            }
        }
    }

    sealed class SplashEvent {
        data class NavigateToLogin(val msg: String?) : SplashEvent()
        object NavigateToHome : SplashEvent()
    }
}