package com.rpfcoding.myposwithjetpackcompose.data.repository

import com.rpfcoding.myposwithjetpackcompose.data.local.AddressDao
import com.rpfcoding.myposwithjetpackcompose.data.local.ModuleDao
import com.rpfcoding.myposwithjetpackcompose.data.local.PositionDao
import com.rpfcoding.myposwithjetpackcompose.data.local.UserDao
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.AddressEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.ModuleEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.PositionEntity
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.UserEntity
import com.rpfcoding.myposwithjetpackcompose.data.remote.MyApi
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.AuthResponse
import com.rpfcoding.myposwithjetpackcompose.data.remote.dto.LoginDto
import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.domain.repository.AuthRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import retrofit2.HttpException
import java.io.IOException

class RemoteAuthRepository(
    private val api: MyApi,
    private val userDao: UserDao,
    private val positionDao: PositionDao,
    private val addressDao: AddressDao,
    private val moduleDao: ModuleDao
) : AuthRepository {

    override suspend fun login(username: String, password: String): Resource<User> {
        return try {
            val result = api.login(LoginDto(username, password))

            if(result.isSuccessful) {
                val authResponse = result.body()
                authResponse?.let {

                    // Clear database every login
                    clearAllTableFromDb()

                    // Insert the response to room db to have single source of truth
                    insertResponseToDb(authResponse)
                }

                val user = userDao.getOne()
                if(user.isEmpty()) {
                    Resource.Error("Cannot login.", null)
                }

                Resource.Success(user[0].toUser())
            }

            Resource.Error("Unknown error occurred.")
        } catch (e: HttpException) {
            Resource.Error(e.message.toString())
        } catch (e: IOException) {
            Resource.Error("Unknown error occurred.")
        }
    }

    private suspend fun clearAllTableFromDb() {
        userDao.deleteAll()
        positionDao.deleteAll()
        addressDao.deleteAll()
        moduleDao.deleteAll()
    }

    private suspend fun insertResponseToDb(authResponse: AuthResponse) {
        userDao.insert(
            UserEntity(
                businessId = authResponse.user.businessId,
                firstName = authResponse.user.firstName,
                middleName = authResponse.user.middleName,
                lastName = authResponse.user.lastName,
                profileImageUrl = authResponse.user.profileImageUrl,
                emailAddress = authResponse.user.profileImageUrl,
                isBusinessOwner = authResponse.user.isBusinessOwner,
                isActive = authResponse.user.isActive,
                positionId = authResponse.user.positionId,
                userId = authResponse.user.userId
            )
        )

        authResponse.user.position?.let { position ->
            positionDao.insert(
                PositionEntity(
                    name = position.name,
                    businessId = position.businessId,
                    userId = authResponse.user.userId,
                    positionId = position.positionId
                )
            )
        }

        authResponse.user.addresses.forEach { address ->
            addressDao.insert(
                AddressEntity(
                    userId = authResponse.user.userId,
                    country = address.country,
                    region = address.region,
                    province = address.province,
                    city = address.city,
                    street = address.street,
                    contactNo = address.contactNo,
                    isDefault = address.isDefault,
                    addressId = address.addressId
                )
            )
        }

        authResponse.user.modules.forEach { module ->
            moduleDao.insert(
                ModuleEntity(
                    name = module.name,
                    userId = authResponse.user.userId,
                    moduleId = module.moduleId
                )
            )
        }
    }
}