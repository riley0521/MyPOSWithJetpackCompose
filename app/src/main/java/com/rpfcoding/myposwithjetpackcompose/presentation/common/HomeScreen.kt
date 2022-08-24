package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.presentation.NavGraphs
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.DashboardScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.MenuScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.SettingsScreenDestination

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isBottomBarVisible = rememberSaveable() {
        mutableStateOf(true)
    }

    val isTopBarVisible = rememberSaveable() {
        mutableStateOf(true)
    }

    when(navBackStackEntry?.destination?.route) {
        DashboardScreenDestination.route, MenuScreenDestination.route, SettingsScreenDestination.route -> {
            isBottomBarVisible.value = true
            isTopBarVisible.value = true
        }
        else -> {
            isBottomBarVisible.value = false
            isTopBarVisible.value = false
        }
    }

    var title by remember {
        mutableStateOf("Dashboard")
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                isVisible = isBottomBarVisible.value
            ) {
                title = it
            }
        },
        topBar = {
            AnimatedVisibility(
                visible = isTopBarVisible.value,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                TopAppBar(
                    elevation = 8.dp,
                    backgroundColor = MaterialTheme.colors.surface,

                    ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.surface,

        ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.home
        )
    }
}