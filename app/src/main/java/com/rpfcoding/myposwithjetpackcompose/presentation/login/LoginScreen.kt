package com.rpfcoding.myposwithjetpackcompose.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.R
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    val context = LocalContext.current

    val errorMsg = stringResource(id = R.string.unknown_error)

    val successMsg = stringResource(id = R.string.login_success)

    LaunchedEffect(key1 = viewModel.loginEvent) {
        viewModel.loginEvent.collectLatest { event ->
            when(event) {
                LoginViewModel.LoginEvent.NavigateToHome -> {
                    Toast.makeText(context, successMsg, Toast.LENGTH_SHORT).show()
                }
                is LoginViewModel.LoginEvent.ShowError -> {
                    Toast.makeText(context, event.msg ?: errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.logging_in))
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    OutlinedTextField(
                        value = state.usernameText,
                        onValueChange = viewModel::onUsernameChange,
                        placeholder = {
                            Text(text = stringResource(id = R.string.hint_username))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.hint_username))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    if (state.usernameError != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.usernameError.asString(),
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.passwordText,
                        onValueChange = viewModel::onPasswordChange,
                        visualTransformation = PasswordVisualTransformation(),
                        placeholder = {
                            Text(text = stringResource(id = R.string.hint_password))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.hint_password))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    if (state.passwordError != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.passwordError.asString(),
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = viewModel::onLoginClick) {
                        Text(text = stringResource(id = R.string.login))
                    }
                }
            }
        }

    }
}