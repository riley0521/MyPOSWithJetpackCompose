package com.rpfcoding.myposwithjetpackcompose.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.presentation.common.HomeNavGraph

@HomeNavGraph(start = true)
@Destination
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row() {
            Text(
                text = stringResource(id = R.string.today_sale),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.todaySale.toString(),
                style = MaterialTheme.typography.h6,
            )
        }
        Row() {
            Text(
                text = stringResource(id = R.string.weekly_sale),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.weeklySale.toString(),
                style = MaterialTheme.typography.h6,
            )
        }
        Row() {
            Text(
                text = stringResource(id = R.string.monthly_sale),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.monthlySale.toString(),
                style = MaterialTheme.typography.h6,
            )
        }
        Row() {
            Text(
                text = stringResource(id = R.string.yearly_sale),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = state.yearlySale.toString(),
                style = MaterialTheme.typography.h6,
            )
        }
    }
}