package com.rpfcoding.myposwithjetpackcompose.presentation.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.presentation.common.HomeNavGraph
import com.rpfcoding.myposwithjetpackcompose.presentation.common.SettingItem
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.ProfileScreenDestination

@HomeNavGraph
@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (state.user != null) {
                SettingItem(
                    icon = Icons.Filled.Person,
                    title = "Profile",
                    onClick = {
                        navigator.navigate(ProfileScreenDestination)
                    }
                )

                if (state.user.isBusinessOwner) {
                    SettingItem(
                        icon = Icons.Filled.Store,
                        title = "Business Settings",
                        onClick = {}
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            SettingItem(
                icon = Icons.Filled.ExitToApp,
                title = "Sign out",
                onClick = {}
            )
        }
    }
}