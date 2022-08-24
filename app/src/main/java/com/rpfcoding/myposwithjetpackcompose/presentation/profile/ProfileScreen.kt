package com.rpfcoding.myposwithjetpackcompose.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.common.HomeNavGraph
import com.rpfcoding.myposwithjetpackcompose.presentation.common.ProfileItem
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.EditProfileScreenDestination

@HomeNavGraph
@Destination
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = state.user.profileImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_person)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileItem(
                title = "${state.user.firstName} ${state.user.middleName} ${state.user.lastName}",
                subtitle = stringResource(id = R.string.name),
                icon = Icons.Filled.Person
            )

            ProfileItem(
                title = state.user.emailAddress.ifBlank { "example@test.com" },
                subtitle = stringResource(id = R.string.email),
                icon = Icons.Filled.Email
            )

            ProfileItem(
                title = if(state.user.position == null && state.user.isBusinessOwner) stringResource(
                    id = R.string.owner
                ) else if(state.user.position != null) state.user.position.name else "N/A",
                subtitle = stringResource(id = R.string.position),
                icon = Icons.Filled.Group
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navigator.navigate(EditProfileScreenDestination(state.user))
                    },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(text = stringResource(id = R.string.btn_edit_profile))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(text = stringResource(id = R.string.btn_view_addresses))
                }
            }
        }
    }
}