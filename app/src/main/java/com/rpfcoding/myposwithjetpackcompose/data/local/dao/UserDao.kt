package com.rpfcoding.myposwithjetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("UPDATE tbl_users SET firstName = :firstName, middleName = :middleName, lastName = :lastName, emailAddress = :email WHERE userId = :userId")
    suspend fun update(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String,
        userId: Int
    )

    @Query("SELECT * FROM tbl_users WHERE userId = :userId")
    suspend fun getById(userId: Int): UserEntity?

    @Query("DELETE FROM tbl_users")
    suspend fun deleteAll()
}