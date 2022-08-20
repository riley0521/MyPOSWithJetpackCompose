package com.rpfcoding.myposwithjetpackcompose.data.repository

import com.rpfcoding.myposwithjetpackcompose.data.remote.ApiAuthEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.LoginDto
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.getAuth
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiAuthEndpoints,
    private val prefRepository: MyPreferencesRepository
) : AuthRepository {

    override suspend fun login(username: String, password: String): Resource<Unit> {
        return try {
            val result = api.login(LoginDto(username, password))

            // Clear database every login
            // clearAllTableFromDb()

            // Insert the response to room db to have single source of truth
            // insertResponseToDb(result)

            // Save response (userId, businessId, token) to sharedPrefs
            prefRepository.saveUserId(result.userId)
            prefRepository.saveBusinessId(result.businessId)
            prefRepository.saveToken(result.token)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error("Invalid username or password.")
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    override suspend fun isAuthenticated(token: String): Resource<Unit> {
        return try {
            api.isAuthenticated(getAuth(token))

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error("Unauthorized / Your token expired. Please log in again.")
        } catch (e: IOException) {
            Resource.Error("Unknown error. Please log in again.")
        }
    }

    //    private suspend fun clearAllTableFromDb() {
//        userDao.deleteAll()
//        positionDao.deleteAll()
//        addressDao.deleteAll()
//        moduleDao.deleteAll()
//    }

//    private suspend fun insertResponseToDb(authResponse: AuthResponse) {
//        userDao.insert(
//            UserEntity(
//                firstName = authResponse.user.firstName,
//                middleName = authResponse.user.middleName,
//                lastName = authResponse.user.lastName,
//                profileImageUrl = authResponse.user.profileImageUrl,
//                emailAddress = authResponse.user.profileImageUrl,
//                isBusinessOwner = authResponse.user.isBusinessOwner,
//                isActive = authResponse.user.isActive,
//                userId = authResponse.user.userId,
//
//            )
//        )
//
//        authResponse.user.position?.let { position ->
//            positionDao.insert(
//                PositionEntity(
//                    name = position.name,
//                    userId = authResponse.user.userId,
//                    positionId = position.positionId
//                )
//            )
//        }
//
//        addressDao.insert(authResponse.user.addresses.map { address ->
//            AddressEntity(
//                userId = authResponse.user.userId,
//                country = address.country,
//                region = address.region,
//                province = address.province,
//                city = address.city,
//                street = address.street,
//                contactNo = address.contactNo,
//                isDefault = address.isDefault,
//                addressId = address.addressId
//            )
//        })
//
//        moduleDao.insert(authResponse.user.modules.map { module ->
//            ModuleEntity(
//                name = module.name,
//                userId = authResponse.user.userId,
//                moduleId = module.moduleId
//            )
//        })
//    }
}