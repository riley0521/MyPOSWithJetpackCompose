package com.rpfcoding.myposwithjetpackcompose.presentation.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.worker.DownloadProductGroupsWorker
import com.rpfcoding.myposwithjetpackcompose.data.worker.DownloadProductsWorker
import com.rpfcoding.myposwithjetpackcompose.data.worker.DownloadUserInfoWorker
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.WORKER_DOWNLOAD_USER_INFORMATION
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefRepository: MyPreferencesRepository,
    private val application: Application
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val workManager = WorkManager.getInstance(application)

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

            verifyUserInput()

            if (state.usernameError == null && state.passwordError == null) {
                when (val result = authRepository.login(state.usernameText, state.passwordText)) {
                    is Resource.Error -> {
                        _loginEventChannel.send(LoginEvent.ShowError(result.message))
                    }
                    is Resource.Success -> {
                        if (isFullyRegistered()) {
                            launchDownloadUserInfoWorker()
                            _loginEventChannel.send(LoginEvent.NavigateToHome)
                        } else {
                            _loginEventChannel.send(LoginEvent.NavigateToRegisterBusiness)
                        }
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun verifyUserInput() {
        state = if (state.usernameText.isBlank()) {
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

        state = if (state.passwordText.isBlank()) {
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
    }

    private suspend fun isFullyRegistered(): Boolean {
        val userId = prefRepository.readUserId().stateIn(viewModelScope).value
        val businessId = prefRepository.readBusinessId().stateIn(viewModelScope).value

        return userId > 0 && businessId > 0
    }

    private fun launchDownloadUserInfoWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val firstWorker = OneTimeWorkRequestBuilder<DownloadUserInfoWorker>()
            .setConstraints(constraints)
            .build()

        var continuation = workManager.beginUniqueWork(
            WORKER_DOWNLOAD_USER_INFORMATION,
            ExistingWorkPolicy.KEEP,
            firstWorker
        )

        val secondWorker = OneTimeWorkRequestBuilder<DownloadProductGroupsWorker>()
            .setConstraints(constraints)
            .build()

        continuation = continuation.then(secondWorker)

        val thirdWorker = OneTimeWorkRequestBuilder<DownloadProductsWorker>()
            .setConstraints(constraints)
            .build()

        continuation = continuation.then(thirdWorker)

        continuation.enqueue()
    }

    sealed class LoginEvent {
        data class ShowError(val msg: UiText?) : LoginEvent()
        object NavigateToHome : LoginEvent()
        object NavigateToRegisterBusiness : LoginEvent()
    }
}