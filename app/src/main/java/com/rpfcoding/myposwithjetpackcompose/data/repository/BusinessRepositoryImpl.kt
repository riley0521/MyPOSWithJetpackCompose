package com.rpfcoding.myposwithjetpackcompose.data.repository

import android.net.Uri
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import androidx.room.util.FileUtil
import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiBusinessEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.BusinessDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.CreatedFileNameResponse
import com.rpfcoding.myposwithjetpackcompose.domain.model.Business
import com.rpfcoding.myposwithjetpackcompose.domain.repository.BusinessRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.net.URI
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class BusinessRepositoryImpl @Inject constructor(
    private val api: ApiBusinessEndpoints,
    private val prefRepository: MyPreferencesRepository
) : BusinessRepository {

    override suspend fun create(
        business: Business,
        selectedImage: File?
    ): Resource<Unit> {
        val token = prefRepository.readToken().stateIn(CoroutineScope(coroutineContext)).value
        return try {
            var businessLogoUrl = ""

            selectedImage?.let {
                val res = api.uploadImage(
                    token,
                    MultipartBody.Part.createFormData(
                        "image",
                            selectedImage.name,
                            selectedImage.asRequestBody()
                    )
                )

                businessLogoUrl = res.fileName
            }

            val result = api.create(
                token,
                BusinessDto(
                    name = business.name,
                    facebookUrl = business.facebookUrl,
                    instagramUrl = business.instagramUrl,
                    twitterUrl = business.twitterUrl,
                    email = business.email,
                    country = business.country,
                    region = business.region,
                    province = business.province,
                    city = business.city,
                    landlineNo = business.landlineNo,
                    street = business.street,
                    businessLogoUrl = businessLogoUrl
                )
            )

            prefRepository.saveBusinessId(result.id)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Log.d("FF", e.message.toString())
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }
}