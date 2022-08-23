package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.navigation.navigate
import com.rpfcoding.myposwithjetpackcompose.presentation.NavGraphs
import com.rpfcoding.myposwithjetpackcompose.presentation.appCurrentDestinationAsState
import com.rpfcoding.myposwithjetpackcompose.presentation.startAppDestination

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomBar(
    navController: NavController,
    onMenuSelected: (String) -> Unit
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val context = LocalContext.current

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.popBackStack(currentDestination.route, true)
                    navController.navigate(destination.direction) {
                        launchSingleTop = true
                    }
                    onMenuSelected(context.getString(destination.label))
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                label = {
                    Text(text = stringResource(id = destination.label))
                }
            )
        }
    }
}