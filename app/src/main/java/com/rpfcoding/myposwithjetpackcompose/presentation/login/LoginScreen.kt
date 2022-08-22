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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MyOutlinedTextField
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.LoginScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.RegisterBusinessScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.RegisterUserScreenDestination
import kotlinx.coroutines.flow.collectLatest

@ExperimentalPermissionsApi
@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    val context = LocalContext.current

    val successMsg = stringResource(id = R.string.login_success)

    LaunchedEffect(key1 = viewModel.loginEvent) {
        viewModel.loginEvent.collectLatest { event ->
            when (event) {
                LoginViewModel.LoginEvent.NavigateToHome -> {
                    Toast.makeText(context, successMsg, Toast.LENGTH_SHORT).show()

                    navigator.popBackStack(LoginScreenDestination, true)
                }
                is LoginViewModel.LoginEvent.ShowError -> {
                    Toast.makeText(context, event.msg?.asString(context), Toast.LENGTH_SHORT).show()
                }
                LoginViewModel.LoginEvent.NavigateToRegisterBusiness -> {
                    navigator.popBackStack(LoginScreenDestination, true)
                    navigator.navigate(RegisterBusinessScreenDestination)
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
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.End
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
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError.asString(),
                            color = MaterialTheme.colors.error
                        )
                    }
                    Button(onClick = viewModel::onLoginClick) {
                        Text(text = stringResource(id = R.string.login))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        navigator.navigate(RegisterUserScreenDestination)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
            }
        }

    }
}