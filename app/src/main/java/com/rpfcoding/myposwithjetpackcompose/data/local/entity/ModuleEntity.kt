package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_modules")
data class ModuleEntity(
    val name: String,
    val userId: Int,
    @PrimaryKey
    val moduleId: Int = 0
)