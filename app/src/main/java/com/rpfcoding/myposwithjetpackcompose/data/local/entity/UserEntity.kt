package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
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
)