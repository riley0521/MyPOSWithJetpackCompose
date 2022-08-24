package com.rpfcoding.myposwithjetpackcompose.data.repository

import com.rpfcoding.myposwithjetpackcompose.R
import com.rpfcoding.myposwithjetpackcompose.data.local.MyDatabase
import com.rpfcoding.myposwithjetpackcompose.data.local.dao.UserDao
import com.rpfcoding.myposwithjetpackcompose.data.remote.endpoint.ApiUserEndpoints
import com.rpfcoding.myposwithjetpackcompose.data.remote.response.UpdateUserRequest
import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.UserRepository
import com.rpfcoding.myposwithjetpackcompose.util.Resource
import com.rpfcoding.myposwithjetpackcompose.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class UserRepositoryImpl @Inject constructor(
    private val api: ApiUserEndpoints,
    private val db: MyDatabase,
    private val prefRepository: MyPreferencesRepository
) : UserRepository {

    override suspend fun getUserInfo(): User? {
        val userId = prefRepository.readUserId().stateIn(CoroutineScope(coroutineContext)).value

        val userDao = db.userDao()
        val positionDao = db.positionDao()
        val addressDao = db.addressDao()

        val position = positionDao.getByUserId(userId)?.toPosition()
        val addresses = addressDao.getByUserId(userId).map { it.toAddress() }

        return userDao.getById(userId)?.toUser(position, addresses)
    }

    override suspend fun saveUser(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String
    ): Resource<Unit> {
        return try {
            val token = prefRepository.readToken().stateIn(CoroutineScope(coroutineContext)).value
            val userId = prefRepository.readUserId().stateIn(CoroutineScope(coroutineContext)).value

            api.update(
                token,
                UpdateUserRequest(
                    firstName = firstName,
                    middleName = middleName,
                    lastName = lastName,
                    emailAddress = email
                )
            )

            db.userDao().update(firstName, middleName, lastName, email, userId)

            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error(UiText.DynamicString(e.response()?.errorBody()?.string() ?: "Unknown error occurred."))
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(resId = R.string.unknown_error))
        }
    }
}