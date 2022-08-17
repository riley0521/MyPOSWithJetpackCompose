package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.User

@Entity(tableName = "tbl_users")
data class UserEntity(
    val businessId: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val profileImageUrl: String,
    val emailAddress: String,
    val isBusinessOwner: Boolean,
    val isActive: Boolean,
    val positionId: Int? = null,
    @PrimaryKey
    val userId: Int = 0,
) {
    fun toUser(): User {
        return User(
            businessId = businessId,
            firstName = firstName,
            middleName = middleName,
            lastName = lastName,
            profileImageUrl = profileImageUrl,
            emailAddress = profileImageUrl,
            isBusinessOwner = isBusinessOwner,
            isActive = isActive,
            positionId = positionId,
            userId = userId
        )
    }
}