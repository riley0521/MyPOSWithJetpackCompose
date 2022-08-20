package com.rpfcoding.myposwithjetpackcompose.presentation.register_business

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.domain.repository.BusinessRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterBusinessViewModel @Inject constructor(
    private val repository: BusinessRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterBusinessState())
        private set

    private val _registerBusinessEventChannel = Channel<RegisterBusinessEvent>()
    val registerBusinessEvent = _registerBusinessEventChannel.receiveAsFlow()

    fun onNameChange(name: String) {
        state = state.copy(nameText = name)
    }

    fun onFacebookUrlChange(url: String) {
        state = state.copy(facebookUrlText = url)
    }

    fun onInstagramUrlChange(url: String) {
        state = state.copy(instagramUrlText = url)
    }

    fun onTwitterUrlChange(url: String) {
        state = state.copy(twitterUrlText = url)
    }

    fun onCountryChange(country: String) {
        state = state.copy(countryText = country)
    }

    fun onRegionChange(region: String) {
        state = state.copy(regionText = region)
    }

    fun onProvinceChange(province: String) {
        state = state.copy(provinceText = province)
    }

    fun onCityChange(city: String) {
        state = state.copy(cityText = city)
    }

    fun onStreetChange(street: String) {
        state = state.copy(streetText = street)
    }

    fun onLandlineNoChange(value: String) {
        state = state.copy(landlineNoText = value)
    }

    fun onEmailChange(email: String) {
        state = state.copy(emailText = email)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            verifyName()
            verifyUrl()
            verifyLocation()

            val pattern = "\\d{3}-\\d{4}"
            state = if (state.landlineNoText.isNotBlank() && !pattern.toRegex()
                    .matches(state.landlineNoText)
            ) {
                state.copy(landlineNoError = UiText.StringResource(resId = R.string.landline_invalid))
            } else {
                state.copy(landlineNoError = null)
            }

            state =
                if (state.emailText.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(state.emailText)
                        .matches()
                ) {
                    state.copy(emailError = UiText.StringResource(resId = R.string.email_not_valid))
                } else {
                    state.copy(emailError = null)
                }

            if (
                state.nameError == null &&
                state.facebookUrlError == null &&
                state.instagramUrlError == null &&
                state.twitterUrlError == null &&
                state.countryError == null &&
                state.regionError == null &&
                state.provinceError == null &&
                state.cityError == null &&
                state.streetError == null &&
                state.landlineNoError == null &&
                state.emailError == null
            ) {
                when (val result = repository.create(
                    name = state.nameText,
                    facebookUrl = state.facebookUrlText,
                    instagramUrl = state.instagramUrlText,
                    twitterUrl = state.twitterUrlText,
                    email = state.emailText,
                    country = state.countryText,
                    region = state.regionText,
                    province = state.provinceText,
                    city = state.cityText,
                    street = state.streetText,
                    landlineNo = state.landlineNoText
                )) {
                    is Resource.Error -> {
                        _registerBusinessEventChannel.send(
                            RegisterBusinessEvent.ShowError(result.message)
                        )
                    }
                    is Resource.Success -> {
                        _registerBusinessEventChannel.send(
                            RegisterBusinessEvent.NavigateToHome
                        )
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun verifyLocation() {
        state = if (state.countryText.isBlank()) {
            state.copy(countryError = UiText.StringResource(resId = R.string.country_required))
        } else {
            state.copy(countryError = null)
        }

        state = if (state.regionText.isBlank()) {
            state.copy(regionError = UiText.StringResource(resId = R.string.region_required))
        } else {
            state.copy(regionError = null)
        }

        state = if (state.provinceText.isBlank()) {
            state.copy(provinceError = UiText.StringResource(resId = R.string.province_required))
        } else {
            state.copy(provinceError = null)
        }

        state = if (state.cityText.isBlank()) {
            state.copy(cityError = UiText.StringResource(resId = R.string.city_required))
        } else {
            state.copy(cityError = null)
        }
    }

    private fun verifyUrl() {
        state =
            if (state.facebookUrlText.isNotBlank() && !Patterns.WEB_URL.matcher(state.facebookUrlText)
                    .matches()
            ) {
                state.copy(facebookUrlError = UiText.StringResource(resId = R.string.facebook_url_invalid))
            } else {
                state.copy(facebookUrlError = null)
            }

        state =
            if (state.instagramUrlText.isNotBlank() && !Patterns.WEB_URL.matcher(state.instagramUrlText)
                    .matches()
            ) {
                state.copy(instagramUrlError = UiText.StringResource(resId = R.string.instagram_url_invalid))
            } else {
                state.copy(instagramUrlError = null)
            }

        state =
            if (state.twitterUrlText.isNotBlank() && !Patterns.WEB_URL.matcher(state.twitterUrlText)
                    .matches()
            ) {
                state.copy(twitterUrlError = UiText.StringResource(resId = R.string.twitter_url_invalid))
            } else {
                state.copy(twitterUrlError = null)
            }
    }

    private fun verifyName() {
        state = if (state.nameText.isBlank()) {
            state.copy(nameError = UiText.StringResource(resId = R.string.business_name_required))
        } else {
            state.copy(nameError = null)
        }
    }

    sealed class RegisterBusinessEvent {
        data class ShowError(val msg: UiText?) : RegisterBusinessEvent()
        object NavigateToHome : RegisterBusinessEvent()
    }
}