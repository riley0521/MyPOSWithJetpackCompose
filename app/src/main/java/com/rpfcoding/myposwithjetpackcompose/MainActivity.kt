package com.rpfcoding.myposwithjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.rpfcoding.myposwithjetpackcompose.presentation.NavGraphs
import com.rpfcoding.myposwithjetpackcompose.ui.theme.MyPOSWithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPOSWithJetpackComposeTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}