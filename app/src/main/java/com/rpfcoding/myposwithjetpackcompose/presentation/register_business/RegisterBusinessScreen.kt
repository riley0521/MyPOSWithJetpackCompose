package com.rpfcoding.myposwithjetpackcompose.presentation.register_business

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
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
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MySpinner
import com.rpfcoding.myposwithjetpackcompose.util.Constants
import com.rpfcoding.myposwithjetpackcompose.util.Constants.countries
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun RegisterBusinessScreen(
    viewModel: RegisterBusinessViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.registerBusinessEvent) {
        viewModel.registerBusinessEvent.collectLatest { event ->
            when (event) {
                RegisterBusinessViewModel.RegisterBusinessEvent.NavigateToHome -> {
                    // TODO
                }
                is RegisterBusinessViewModel.RegisterBusinessEvent.ShowError -> {
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
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                    }.align(Alignment.CenterHorizontally),
                imageVector = Icons.Filled.Person,
                contentDescription = stringResource(id = R.string.person)
            )
            MyOutlinedTextField(
                value = state.nameText,
                onValueChange = viewModel::onNameChange,
                placeholder = stringResource(id = R.string.hint_business_name),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nameError != null) {
                Text(
                    text = state.nameError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.facebookUrlText,
                onValueChange = viewModel::onFacebookUrlChange,
                placeholder = stringResource(id = R.string.hint_facebook_url),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.facebookUrlError != null) {
                Text(
                    text = state.facebookUrlError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.instagramUrlText,
                onValueChange = viewModel::onInstagramUrlChange,
                placeholder = stringResource(id = R.string.hint_instagram_url),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.instagramUrlError != null) {
                Text(
                    text = state.instagramUrlError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.twitterUrlText,
                onValueChange = viewModel::onTwitterUrlChange,
                placeholder = stringResource(id = R.string.hint_twitter_url),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.twitterUrlError != null) {
                Text(
                    text = state.twitterUrlError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MySpinner(
                onItemSelected = viewModel::onCountryChange
            )
            if (state.countryError != null) {
                Text(
                    text = state.countryError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.regionText,
                onValueChange = viewModel::onRegionChange,
                placeholder = stringResource(id = R.string.hint_region),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.regionError != null) {
                Text(
                    text = state.regionError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.provinceText,
                onValueChange = viewModel::onProvinceChange,
                placeholder = stringResource(id = R.string.hint_province),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.provinceError != null) {
                Text(
                    text = state.provinceError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.cityText,
                onValueChange = viewModel::onCityChange,
                placeholder = stringResource(id = R.string.hint_city),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.cityError != null) {
                Text(
                    text = state.cityError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.streetText,
                onValueChange = viewModel::onStreetChange,
                placeholder = stringResource(id = R.string.hint_street),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.streetError != null) {
                Text(
                    text = state.streetError.asString(),
                    color = MaterialTheme.colors.error
                )
            }
            MyOutlinedTextField(
                value = state.landlineNoText,
                onValueChange = viewModel::onLandlineNoChange,
                placeholder = stringResource(id = R.string.hint_landline),
                modifier = Modifier.fillMaxWidth(),
                keyboardOpts = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            if (state.landlineNoError != null) {
                Text(
                    text = state.landlineNoError.asString(),
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