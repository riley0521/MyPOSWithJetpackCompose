package com.rpfcoding.myposwithjetpackcompose.data.remote.dto

data class BusinessDto(
    val name: String,
    val facebookUrl: String,
    val instagramUrl: String,
    val twitterUrl: String,
    val email: String,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val landlineNo: String = "",
    val businessLogoUrl: String = "",
    val street: String = "",
    val businessId: Int = 0,
    val currencies: List<CurrencyDto> = emptyList(),
    val listOfUnit: List<UOMDto> = emptyList(),
    val sizeTypes: List<SizeTypeDto> = emptyList()
)
