package com.rpfcoding.myposwithjetpackcompose.presentation.register_user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MyOutlinedTextField
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.RegisterBusinessScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.RegisterUserScreenDestination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun RegisterUserScreen(
    viewModel: RegisterUserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.registerUserEvent) {
        viewModel.registerUserEvent.collectLatest { event ->
            when (event) {
                RegisterUserViewModel.RegisterUserEvent.NavigateToRegisterBusiness -> {
                    navigator.popBackStack(RegisterUserScreenDestination, true)
                    navigator.navigate(RegisterBusinessScreenDestination)
                }
                is RegisterUserViewModel.RegisterUserEvent.ShowError -> {
                    Toast.makeText(context, event.msg?.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End,
        ) {
            MyOutlinedTextField(
                value = state.usernameText,
                onValueChange = viewModel::onUsernameChange,
                placeholder = stringResource(id = R.string.hint_username),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.usernameError != null) {
                Text(
                    text = state.usernameError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.passwordText,
                onValueChange = viewModel::onPasswordChange,
                placeholder = stringResource(id = R.string.hint_password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.confirmPassText,
                onValueChange = viewModel::onConfirmPassChange,
                placeholder = stringResource(id = R.string.hint_confirm_password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.confirmPassError != null) {
                Text(
                    text = state.confirmPassError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.firstNameText,
                onValueChange = viewModel::onFirstNameChange,
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
                onValueChange = viewModel::onMiddleNameChange,
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
                onValueChange = viewModel::onLastNameChange,
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
                onValueChange = viewModel::onEmailChange,
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
            Button(
                onClick = viewModel::onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }
    }
}