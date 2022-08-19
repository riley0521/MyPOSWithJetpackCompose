package com.rpfcoding.myposwithjetpackcompose.domain.model

data class Business(
    val name: String,
    val businessLogoUrl: String,
    val facebookUrl: String,
    val instagramUrl: String,
    val twitterUrl: String,
    val landlineNo: String,
    val email: String,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val currencies: List<Currency>
)
