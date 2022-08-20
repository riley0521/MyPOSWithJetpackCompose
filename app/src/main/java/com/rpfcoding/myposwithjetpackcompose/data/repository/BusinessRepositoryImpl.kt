package com.rpfcoding.myposwithjetpackcompose.data.repository

import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.remote.ApiBusinessEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.BusinessDto
import com.rpfcoding.myposwithjetpackcompose.domain.repository.BusinessRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class BusinessRepositoryImpl @Inject constructor(
    private val api: ApiBusinessEndpoints,
    private val prefRepository: MyPreferencesRepository
) : BusinessRepository {

    override suspend fun create(
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
    ): Resource<Unit> {
        return try {
            val token = prefRepository.readToken().stateIn(CoroutineScope(coroutineContext)).value

            val result = api.create(
                token,
                BusinessDto(
                    name = name,
                    facebookUrl = facebookUrl,
                    instagramUrl = instagramUrl,
                    twitterUrl = twitterUrl,
                    email = email,
                    country = country,
                    region = region,
                    province = province,
                    city = city,
                    landlineNo = landlineNo,
                    street = street
                )
            )

            prefRepository.saveBusinessId(result.businessId)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }
}