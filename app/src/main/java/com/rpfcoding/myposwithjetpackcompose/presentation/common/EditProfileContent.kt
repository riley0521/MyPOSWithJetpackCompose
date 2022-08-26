package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.edit_profile.EditProfileState

@Composable
fun EditProfileContent(
    state: EditProfileState,
    onFirstNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSaveProfileClick: () -> Unit
) {
    MyOutlinedTextField(
        value = state.firstNameText,
        onValueChange = onFirstNameChange,
        leadingIcon = Icons.Filled.Person,
        placeholder = stringResource(id = R.string.hint_first_name),
        modifier = Modifier.fillMaxWidth()
    )
    if (state.firstNameError != null) {
        Text(
            text = state.firstNameError.asString(),
            color = MaterialTheme.colors.error
        )
    }
    MyOutlinedTextField(
        value = state.middleNameText,
        onValueChange = onMiddleNameChange,
        leadingIcon = Icons.Filled.Person,
        placeholder = stringResource(id = R.string.hint_middle_name),
        modifier = Modifier.fillMaxWidth()
    )
    if (state.middleNameError != null) {
        Text(
            text = state.middleNameError.asString(),
            color = MaterialTheme.colors.error
        )
    }
    MyOutlinedTextField(
        value = state.lastNameText,
        onValueChange = onLastNameChange,
        leadingIcon = Icons.Filled.Person,
        placeholder = stringResource(id = R.string.hint_last_name),
        modifier = Modifier.fillMaxWidth()
    )
    if (state.lastNameError != null) {
        Text(
            text = state.lastNameError.asString(),
            color = MaterialTheme.colors.error
        )
    }
    MyOutlinedTextField(
        value = state.emailText,
        onValueChange = onEmailChange,
        leadingIcon = Icons.Filled.Email,
        placeholder = stringResource(id = R.string.hint_email),
        modifier = Modifier.fillMaxWidth(),
        keyboardOpts = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    if (state.emailError != null) {
        Text(
            text = state.emailError.asString(),
            color = MaterialTheme.colors.error
        )
    }
    if(state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator()
        }
    } else {
        Button(
            onClick = onSaveProfileClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isDifferent
        ) {
            Text(text = stringResource(id = R.string.btn_save_profile))
        }
    }
}