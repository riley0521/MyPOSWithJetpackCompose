package com.rpfcoding.myposwithjetpackcompose.domain.repository

import android.net.Uri
import com.rpfcoding.myposwithjetpackcompose.domain.model.Business
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import java.io.File

interface BusinessRepository {

    suspend fun create(
        business: Business,
        selectedImage: File?
    ): Resource<Unit>
}