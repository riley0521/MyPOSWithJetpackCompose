package com.rpfcoding.myposwithjetpackcompose.presentation.edit_profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.presentation.common.EditProfileContent
import com.rpfcoding.myposwithjetpackcompose.presentation.common.HomeNavGraph
import kotlinx.coroutines.flow.collectLatest

@HomeNavGraph
@Destination
@Composable
fun EditProfileScreen(
    user: User,
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.editProfileEvent) {
        viewModel.editProfileEvent.collectLatest { event ->
            when(event) {
                EditProfileViewModel.EditProfileEvent.NavigateBack -> {
                    navigator.popBackStack()
                }
                is EditProfileViewModel.EditProfileEvent.ShowError -> {
                    if(event.msg != null) {
                        Toast.makeText(context, event.msg.asString(context), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End,
    ) {
        EditProfileContent(
            state = state,
            onFirstNameChange = viewModel::onFirstNameChange,
            onMiddleNameChange = viewModel::onMiddleNameChange,
            onLastNameChange = viewModel::onLastNameChange,
            onEmailChange = viewModel::onEmailChange,
            onSaveProfileClick = viewModel::onSaveProfileClick
        )
    }
}