package com.rpfcoding.myposwithjetpackcompose.presentation.register_business

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkInfo
import coil.compose.AsyncImage
import com.google.accompanist.permissions.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MyOutlinedTextField
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MySpinner
import kotlinx.coroutines.flow.collectLatest
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.HomeScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.RegisterBusinessScreenDestination
import com.rpfcoding.myposwithjetpackcompose.util.Constants
import com.rpfcoding.myposwithjetpackcompose.util.Constants.countries
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import java.io.File

@ExperimentalPermissionsApi
@Destination
@Composable
fun RegisterBusinessScreen(
    viewModel: RegisterBusinessViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state


    val context = LocalContext.current

    viewModel.outputWorkInfoList.observe(LocalLifecycleOwner.current) { workInfoList ->
        if (workInfoList.isEmpty()) {
            return@observe
        }

        val worker = workInfoList[0]

        if (worker.state.isFinished) {
            navigator.popBackStack(RegisterBusinessScreenDestination, true)
            navigator.navigate(HomeScreenDestination)
        }
    }

    val galleryPermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {

                val file = File(context.cacheDir, "myImage.jpg")
                file.createNewFile()
                val inputStream = context.contentResolver.openInputStream(uri)
                file.outputStream().use {
                    inputStream?.copyTo(it)
                }

                viewModel.onImageSelect(file.toUri())
            }
        }
    )

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        RegisterBusinessContent(
            viewModel.selectedImageUri,
            galleryPermissionState,
            state,
            onImagePick = {
                imagePicker.launch("image/*")
            },
            onNameChange = viewModel::onNameChange,
            onFacebookUrlChange = viewModel::onFacebookUrlChange,
            onInstagramUrlChange = viewModel::onInstagramUrlChange,
            onTwitterUrlChange = viewModel::onTwitterUrlChange,
            onCountryChange = viewModel::onCountryChange,
            onRegionChange = viewModel::onRegionChange,
            onProvinceChange = viewModel::onProvinceChange,
            onCityChange = viewModel::onCityChange,
            onStreetChange = viewModel::onStreetChange,
            onLandlineNoChange = viewModel::onLandlineNoChange,
            onEmailChange = viewModel::onEmailChange,
            onRegisterClick = viewModel::onRegisterClick
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RegisterBusinessContent(
    selectedImageUri: Uri? = null,
    galleryPermissionState: PermissionState,
    state: RegisterBusinessState = RegisterBusinessState(),
    onImagePick: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onFacebookUrlChange: (String) -> Unit = {},
    onInstagramUrlChange: (String) -> Unit = {},
    onTwitterUrlChange: (String) -> Unit = {},
    onCountryChange: (String) -> Unit = {},
    onRegionChange: (String) -> Unit = {},
    onProvinceChange: (String) -> Unit = {},
    onCityChange: (String) -> Unit = {},
    onStreetChange: (String) -> Unit = {},
    onLandlineNoChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End,
    ) {
        if (selectedImageUri == null) {
            Image(
                modifier = getImageModifier(
                    galleryPermissionState = galleryPermissionState,
                    onImagePick = {
                        onImagePick()
                    }
                ).align(Alignment.CenterHorizontally),
                imageVector = Icons.Filled.Person,
                contentDescription = stringResource(id = R.string.person)
            )
        } else {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Selected image",
                modifier = getImageModifier(
                    galleryPermissionState = galleryPermissionState,
                    onImagePick = {
                        onImagePick()
                    }
                ).align(Alignment.CenterHorizontally)
            )
        }
        MyOutlinedTextField(
            value = state.nameText,
            onValueChange = onNameChange,
            leadingIcon = Icons.Filled.Store,
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
            onValueChange = onFacebookUrlChange,
            leadingIcon = Icons.Filled.Link,
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
            onValueChange = onInstagramUrlChange,
            leadingIcon = Icons.Filled.Link,
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
            onValueChange = onTwitterUrlChange,
            leadingIcon = Icons.Filled.Link,
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
            title = "Countries",
            items = countries,
            onItemSelected = onCountryChange
        )
        if (state.countryError != null) {
            Text(
                text = state.countryError.asString(),
                color = MaterialTheme.colors.error
            )
        }
        MyOutlinedTextField(
            value = state.regionText,
            onValueChange = onRegionChange,
            leadingIcon = Icons.Filled.Place,
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
            onValueChange = onProvinceChange,
            leadingIcon = Icons.Filled.Place,
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
            onValueChange = onCityChange,
            leadingIcon = Icons.Filled.Place,
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
            onValueChange = onStreetChange,
            leadingIcon = Icons.Filled.Place,
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
            onValueChange = onLandlineNoChange,
            leadingIcon = Icons.Filled.Phone,
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
        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun getImageModifier(
    galleryPermissionState: PermissionState,
    onImagePick: () -> Unit
): Modifier = Modifier
    .size(120.dp)
    .clickable {
        when {
            galleryPermissionState.status.isGranted -> {
                onImagePick()
            }
            !galleryPermissionState.status.isGranted -> {
                galleryPermissionState.launchPermissionRequest()
            }
            galleryPermissionState.status.shouldShowRationale -> Unit
            !galleryPermissionState.status.isGranted && !galleryPermissionState.status.shouldShowRationale -> Unit
        }
    }

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun RegisterBusinessContentPreview() {
    RegisterBusinessContent(galleryPermissionState = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE))
}
