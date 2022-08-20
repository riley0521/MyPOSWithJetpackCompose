package com.rpfcoding.myposwithjetpackcompose.presentation.register_business

import com.rpfcoding.myposwithjetpackcompose.util.Constants.countries
import com.rpfcoding.myposwithjetpackcompose.util.UiText

data class RegisterBusinessState(
    val nameText: String = "",
    val nameError: UiText? = null,
    val facebookUrlText: String = "",
    val facebookUrlError: UiText? = null,
    val instagramUrlText: String = "",
    val instagramUrlError: UiText? = null,
    val twitterUrlText: String = "",
    val twitterUrlError: UiText? = null,
    val countryText: String = countries[0],
    val countryError: UiText? = null,
    val regionText: String = "",
    val regionError: UiText? = null,
    val provinceText: String = "",
    val provinceError: UiText? = null,
    val cityText: String = "",
    val cityError: UiText? = null,
    val streetText: String = "",
    val streetError: UiText? = null,
    val landlineNoText: String = "",
    val landlineNoError: UiText? = null,
    val emailText: String = "",
    val emailError: UiText? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
