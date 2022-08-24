package com.rpfcoding.myposwithjetpackcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Business(
    val name: String,
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
    val businessLogoUrl: String = "",
    val currencies: List<Currency> = emptyList(),
    val listOfUnit: List<UnitOfMeasurement> = emptyList(),
    val sizeTypes: List<SizeType> = emptyList()
) : Parcelable
