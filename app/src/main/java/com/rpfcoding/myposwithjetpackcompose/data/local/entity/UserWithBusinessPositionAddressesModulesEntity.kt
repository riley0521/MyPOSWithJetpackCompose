package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Suppress("unused")
@Entity
data class UserWithBusinessPositionAddressesModulesEntity(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId",
    )
    val business: BusinessEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val position: PositionEntity?,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val addresses: List<AddressEntity>,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val modules: List<ModuleEntity>
)
