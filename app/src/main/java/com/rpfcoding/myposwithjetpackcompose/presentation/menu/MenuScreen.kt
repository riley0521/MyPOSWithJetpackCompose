package com.rpfcoding.myposwithjetpackcompose.presentation.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.presentation.common.HomeNavGraph
import com.rpfcoding.myposwithjetpackcompose.presentation.common.MenuItem
import com.rpfcoding.myposwithjetpackcompose.util.Constants.moduleItems

@HomeNavGraph
@Destination
@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(bottom = 50.dp)
        ) {
            items(
                items = moduleItems,
                key = {
                    it.moduleId
                }
            ) { menu ->
                state.modules.forEach { module ->
                    if (module.moduleId == menu.moduleId) {
                        MenuItem(
                            title = menu.title,
                            icon = menu.icon,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}