package com.rpfcoding.myposwithjetpackcompose.domain.repository

import com.rpfcoding.myposwithjetpackcompose.domain.model.User
import com.rpfcoding.myposwithjetpackcompose.util.Resource

interface UserRepository {

    suspend fun getUserInfo(): User?

    suspend fun saveUser(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String
    ): Resource<Unit>
}