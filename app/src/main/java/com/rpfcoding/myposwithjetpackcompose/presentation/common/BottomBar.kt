package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
    isVisible: Boolean,
    onMenuSelected: (String) -> Unit
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val context = LocalContext.current

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
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
}