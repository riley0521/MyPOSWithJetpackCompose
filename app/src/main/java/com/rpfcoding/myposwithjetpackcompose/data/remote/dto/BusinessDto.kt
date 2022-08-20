package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class BusinessDto(
    val businessId: Int,
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
    val currencies: List<CurrencyDto>,
    val listOfUnit: List<UOMDto>,
    val sizeTypes: List<SizeTypeDto>
)
