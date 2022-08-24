package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.Address
import com.rpfcoding.myposwithjetpackcompose.domain.model.Position
import com.rpfcoding.myposwithjetpackcompose.domain.model.User

@Entity(tableName = "tbl_users")
data class UserEntity(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val profileImageUrl: String,
    val emailAddress: String,
    val isBusinessOwner: Boolean,
    val isActive: Boolean,
    @PrimaryKey
    val userId: Int = 0,
) {
    fun toUser(
        position: Position?,
        addresses: List<Address>
    ) = User(
        firstName = firstName,
        middleName = middleName,
        lastName = lastName,
        profileImageUrl = profileImageUrl,
        emailAddress = emailAddress,
        isBusinessOwner = isBusinessOwner,
        isActive = isActive,
        position = position,
        addresses = addresses
    )
}