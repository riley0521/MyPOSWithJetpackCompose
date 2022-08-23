package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.DashboardScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.MenuScreenDestination
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.SettingsScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Dashboard(DashboardScreenDestination, Icons.Filled.Home, R.string.dashboard),
    Menu(MenuScreenDestination, Icons.Filled.Menu, R.string.menu),
    Settings(SettingsScreenDestination, Icons.Filled.Settings, R.string.settings)
}