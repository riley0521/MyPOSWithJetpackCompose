package com.rpfcoding.myposwithjetpackcompose.presentation.edit_profile

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.autoSaver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.domain.repository.UserRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import com.rpfcoding.myposwithjetpackcompose.util.isLetters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by savedStateHandle.saveable { mutableStateOf(EditProfileState()) }
        private set

    private val _editProfileEventChannel = Channel<EditProfileEvent>()
    val editProfileEvent = _editProfileEventChannel.receiveAsFlow()

    init {
        setState()
    }

    private fun setState() {
        val user = savedStateHandle.get<User>("user") ?: return

        state = state.copy(
            firstNameText = user.firstName,
            middleNameText = user.middleName,
            lastNameText = user.lastName,
            emailText = user.emailAddress
        )
    }

    fun onFirstNameChange(value: String) {
        state = state.copy(firstNameText = value)
        state = state.copy(isDifferent = isDifferent())
    }

    fun onMiddleNameChange(value: String) {
        state = state.copy(middleNameText = value)
        state = state.copy(isDifferent = isDifferent())
    }

    fun onLastNameChange(value: String) {
        state = state.copy(lastNameText = value)
        state = state.copy(isDifferent = isDifferent())
    }

    fun onEmailChange(value: String) {
        state = state.copy(emailText = value)
        state = state.copy(isDifferent = isDifferent())
    }

    private fun isDifferent(): Boolean {
        val user = savedStateHandle.get<User>("user") ?: return false
        return user.firstName != state.firstNameText ||
                user.middleName != state.middleNameText ||
                user.lastName != state.lastNameText ||
                user.emailAddress != state.emailText
    }

    fun onSaveProfileClick() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            verifyState()

            if (
                state.firstNameError == null &&
                state.middleNameError == null &&
                state.lastNameError == null &&
                state.emailError == null
            ) {
                delay(1500L)

                when (val result = userRepository.saveUser(
                    state.firstNameText,
                    state.middleNameText,
                    state.lastNameText,
                    state.emailText
                )) {
                    is Resource.Error -> {
                        _editProfileEventChannel.send(
                            EditProfileEvent.ShowError(result.message)
                        )
                    }
                    is Resource.Success -> {
                        _editProfileEventChannel.send(
                            EditProfileEvent.NavigateBack
                        )
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun verifyState() {
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

        state = if (state.emailText.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(state.emailText)
                .matches()
        ) {
            state.copy(emailError = UiText.StringResource(R.string.email_not_valid))
        } else {
            state.copy(emailError = null)
        }
    }

    sealed class EditProfileEvent {
        data class ShowError(val msg: UiText? = null) : EditProfileEvent()
        object NavigateBack : EditProfileEvent()
    }
}