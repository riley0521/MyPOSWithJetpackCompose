package com.rpfcoding.myposwithjetpackcompose.presentation.splash

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.presentation.destinations.LoginScreenDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.splashEventChannel) {
        delay(1500L)

        viewModel.splashEventChannel.collectLatest { event ->
            when(event) {
                SplashViewModel.SplashEvent.NavigateToHome -> {
                    // TODO: Redirect to homeScreen
                    Toast.makeText(context, "Navigating to home...", Toast.LENGTH_SHORT).show()
                }
                is SplashViewModel.SplashEvent.NavigateToLogin -> {
                    if(event.msg != null) {
                        Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                    }
                    navigator.navigate(LoginScreenDestination)
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Splash screen example...")
        }
    }
}