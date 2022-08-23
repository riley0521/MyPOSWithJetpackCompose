package com.rpfcoding.myposwithjetpackcompose.presentation.dashboard

data class DashboardState(
    val todaySale: Double = 0.0,
    val weeklySale: Double = 0.0,
    val monthlySale: Double = 0.0,
    val yearlySale: Double = 0.0,
    val error: String? = null,
    val isLoading: Boolean = false
)
