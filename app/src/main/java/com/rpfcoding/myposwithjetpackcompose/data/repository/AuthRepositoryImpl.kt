package com.rpfcoding.myposwithjetpackcompose.data.repository

import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.local.MyDatabase
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.*
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.BusinessDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiAuthEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.LoginDto
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.RegisterUserDto
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiAuthEndpoints,
    private val db: MyDatabase,
    private val prefRepository: MyPreferencesRepository
) : AuthRepository {

    override suspend fun login(username: String, password: String): Resource<Unit> {
        return try {
            val result = api.login(LoginDto(username, password))

            // Save response (userId, businessId, token) to sharedPrefs
            prefRepository.saveUserId(result.userId)
            prefRepository.saveBusinessId(result.businessId)
            prefRepository.saveToken(result.token)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }

    override suspend fun isAuthenticated(token: String): Resource<Unit> {
        return try {
            api.isAuthenticated(token)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPass: String,
        firstName: String,
        middleName: String,
        lastName: String,
        email: String
    ): Resource<Unit> {
        return try {
            val result = api.register(
                RegisterUserDto(
                    username = username,
                    password = password,
                    confirmPassword = confirmPass,
                    firstName = firstName,
                    middleName = middleName,
                    lastName = lastName,
                    emailAddress = email
                )
            )

            // Save response (userId, businessId, token) to sharedPrefs
            prefRepository.saveUserId(result.userId)
            prefRepository.saveBusinessId(result.businessId)
            prefRepository.saveToken(result.token)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }

    override suspend fun downloadInfo(token: String): Resource<Unit> {
        return try {
            val result = api.downloadInfo(token)

            val userDao = db.userDao()
            val positionDao = db.positionDao()
            val addressDao = db.addressDao()
            val moduleDao = db.moduleDao()

            userDao.insert(
                UserEntity(
                    firstName = result.user.firstName,
                    middleName = result.user.middleName,
                    lastName = result.user.lastName,
                    profileImageUrl = result.user.profileImageUrl,
                    emailAddress = result.user.emailAddress,
                    isBusinessOwner = result.user.isBusinessOwner,
                    isActive = result.user.isActive,
                    userId = result.user.userId
                )
            )

            result.user.business?.let { business ->
                saveBusinessToDb(business, result.user.userId)
            }

            result.user.position?.let { position ->
                positionDao.insert(
                    PositionEntity(
                        name = position.name,
                        userId = result.user.userId,
                        positionId = position.positionId
                    )
                )
            }

            addressDao.insert(result.user.addresses.map { address ->
                AddressEntity(
                    userId = result.user.userId,
                    country = address.country,
                    region = address.region,
                    province = address.province,
                    city = address.city,
                    street = address.street,
                    contactNo = address.contactNo,
                    isDefault = address.isDefault,
                    addressId = address.addressId
                )
            })

            moduleDao.insert(result.user.modules.map { module ->
                ModuleEntity(
                    name = module.name,
                    userId = result.user.userId,
                    moduleId = module.moduleId
                )
            })

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: ""))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }

    private suspend fun saveBusinessToDb(business: BusinessDto, userId: Int) {
        val businessDao = db.businessDao()
        val currencyDao = db.currencyDao()
        val uomDao = db.uomDao()
        val sizeTypeDao = db.sizeTypeDao()
        val sizeVariantDao = db.sizeVariantDao()

        businessDao.insert(
            BusinessEntity(
                name = business.name,
                businessLogoUrl = business.businessLogoUrl,
                facebookUrl = business.facebookUrl,
                instagramUrl = business.instagramUrl,
                twitterUrl = business.twitterUrl,
                landlineNo = business.landlineNo,
                email = business.email,
                country = business.email,
                region = business.region,
                province = business.province,
                city = business.city,
                street = business.street,
                userId = userId,
                businessId = business.businessId
            )
        )

        currencyDao.insert(business.currencies.map {
            CurrencyEntity(
                description = it.description,
                unit = it.unit,
                symbol = it.symbol,
                businessId = business.businessId,
                currencyId = it.currencyId
            )
        })

        uomDao.insert(business.listOfUnit.map {
            UOMEntity(
                name = it.name,
                symbol = it.symbol,
                type = it.type,
                businessId = business.businessId,
                unitOfMeasurementId = it.unitOfMeasurementId
            )
        })

        sizeTypeDao.insert(business.sizeTypes.map {
            SizeTypeEntity(
                name = it.name,
                businessId = business.businessId,
                sizeTypeId = it.sizeTypeId
            )
        })

        business.sizeTypes.forEach { sizeType ->
            sizeVariantDao.insert(sizeType.sizeVariants.map {
                SizeVariantEntity(
                    name = it.name,
                    sizeTypeId = sizeType.sizeTypeId,
                    sizeVariantId = it.sizeVariantId
                )
            })
        }
    }
}