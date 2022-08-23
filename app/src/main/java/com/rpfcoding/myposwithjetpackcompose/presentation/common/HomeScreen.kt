package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.presentation.NavGraphs

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    val navController = rememberNavController()
    var title by remember {
        mutableStateOf("Dashboard")
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController) {
                title = it
            }
        },
        topBar = {
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
        },
        backgroundColor = MaterialTheme.colors.surface,

    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.home
        )
    }
}