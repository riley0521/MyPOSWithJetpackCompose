package com.rpfcoding.myposwithjetpackcompose.presentation.register_user

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.MIN_PASSWORD_LENGTH
import com.rpfcoding.myposwithjetpackcompose.util.Constants.MIN_USERNAME_LENGTH
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import com.rpfcoding.myposwithjetpackcompose.util.isLetters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterUserState())
        private set

    private val _registerUserEventChannel = Channel<RegisterUserEvent>()
    val registerUserEvent = _registerUserEventChannel.receiveAsFlow()

    fun onUsernameChange(username: String) {
        state = state.copy(usernameText = username)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(passwordText = password)
    }

    fun onConfirmPassChange(confirmPass: String) {
        state = state.copy(confirmPassText = confirmPass)
    }

    fun onFirstNameChange(firstName: String) {
        state = state.copy(firstNameText = firstName)
    }

    fun onMiddleNameChange(middleName: String) {
        state = state.copy(middleNameText = middleName)
    }

    fun onLastNameChange(lastName: String) {
        state = state.copy(lastNameText = lastName)
    }

    fun onEmailChange(email: String) {
        state = state.copy(emailText = email)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            verifyUsername()
            verifyPassword()
            verifyName()
            verifyEmail()

            if (
                state.usernameError == null &&
                state.passwordError == null &&
                state.confirmPassError == null &&
                state.firstNameError == null &&
                state.middleNameError == null &&
                state.lastNameError == null &&
                state.emailError == null
            ) {
                when(val result = authRepository.register(
                    username = state.usernameText,
                    password = state.passwordText,
                    confirmPass = state.confirmPassText,
                    firstName = state.firstNameText,
                    middleName = state.middleNameText,
                    lastName = state.lastNameText,
                    email = state.emailText
                )) {
                    is Resource.Error -> {
                        _registerUserEventChannel.send(
                            RegisterUserEvent.ShowError(
                                result.message
                            )
                        )
                    }
                    is Resource.Success -> {
                        _registerUserEventChannel.send(
                            RegisterUserEvent.NavigateToRegisterBusiness
                        )
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun verifyEmail() {
        state = if (state.emailText.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(state.emailText)
                .matches()
        ) {
            state.copy(emailError = UiText.StringResource(R.string.email_not_valid))
        } else {
            state.copy(emailError = null)
        }
    }

    private fun verifyName() {
        state = if (state.firstNameText.isBlank()) {
            state.copy(firstNameError = UiText.StringResource(R.string.firstname_not_blank))
        } else if (!isLetters(state.firstNameText)) {
            state.copy(firstNameError = UiText.StringResource(R.string.firstname_should_contain_letters))
        } else {
            state.copy(firstNameError = null)
        }

        state = if (state.middleNameText.isNotBlank() && !isLetters(state.middleNameText)) {
            state.copy(middleNameError = UiText.StringResource(R.string.middlename_should_contain_letters))
        } else {
            state.copy(middleNameError = null)
        }

        state = if (state.lastNameText.isBlank()) {
            state.copy(lastNameError = UiText.StringResource(R.string.lastname_not_blank))
        } else if (!isLetters(state.lastNameText)) {
            state.copy(lastNameError = UiText.StringResource(R.string.lastname_should_contain_letters))
        } else {
            state.copy(lastNameError = null)
        }
    }

    private fun verifyPassword() {
        state = if (state.passwordText.length < MIN_PASSWORD_LENGTH) {
            state.copy(
                passwordError = UiText.StringResource(
                    R.string.password_min_length,
                    MIN_PASSWORD_LENGTH
                )
            )
        } else {
            state.copy(passwordError = null)
        }

        state = if (state.passwordText != state.confirmPassText) {
            state.copy(confirmPassError = UiText.StringResource(R.string.password_does_not_match))
        } else {
            state.copy(confirmPassError = null)
        }
    }

    private fun verifyUsername() {
        state = if (state.usernameText.length < MIN_USERNAME_LENGTH) {
            state.copy(
                usernameError = UiText.StringResource(
                    R.string.username_min_length,
                    MIN_USERNAME_LENGTH
                )
            )
        } else {
            state.copy(usernameError = null)
        }
    }

    sealed class RegisterUserEvent {
        data class ShowError(val msg: UiText?): RegisterUserEvent()
        object NavigateToRegisterBusiness : RegisterUserEvent()
    }
}