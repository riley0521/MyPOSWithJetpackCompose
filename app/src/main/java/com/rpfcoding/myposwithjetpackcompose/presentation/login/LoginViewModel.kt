package com.rpfcoding.myposwithjetpackcompose.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _loginEventChannel = Channel<LoginEvent>()
    val loginEvent = _loginEventChannel.receiveAsFlow()

    fun onUsernameChange(username: String) {
        state = state.copy(
            usernameText = username
        )
    }

    fun onPasswordChange(password: String) {
        state = state.copy(
            passwordText = password
        )
    }

    fun onLoginClick() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            state = if(state.usernameText.isBlank()) {
                state.copy(
                    usernameError = UiText.StringResource(
                        resId = R.string.username_not_blank,
                    )
                )
            } else {
                state.copy(
                    usernameError = null
                )
            }

            state = if(state.passwordText.isBlank()) {
                state.copy(
                    passwordError = UiText.StringResource(
                        resId = R.string.password_not_blank
                    )
                )
            } else {
                state.copy(
                    passwordError = null
                )
            }

            if(state.usernameError == null && state.passwordError == null) {
                when(val result = repository.login(state.usernameText, state.passwordText)) {
                    is Resource.Error -> {
                        _loginEventChannel.send(LoginEvent.ShowError(result.message))
                    }
                    is Resource.Success -> {
                        _loginEventChannel.send(LoginEvent.NavigateToHome)
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    sealed class LoginEvent {
        data class ShowError(val msg: String?): LoginEvent()
        object NavigateToHome : LoginEvent()
    }
}