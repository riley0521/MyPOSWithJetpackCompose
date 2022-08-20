package com.rpfcoding.myposwithjetpackcompose.domain.repository

import com.rpfcoding.myposwithjetpackcompose.util.Resource

interface BusinessRepository {

    suspend fun create(
        name: String,
        facebookUrl: String,
        instagramUrl: String,
        twitterUrl: String,
        email: String,
        country: String,
        region: String,
        province: String,
        city: String,
        street: String,
        landlineNo: String
    ): Resource<Unit>
}