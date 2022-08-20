package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class SizeTypeWithSizeVariants(
    @Embedded val sizeType: SizeTypeEntity,
    @Relation(
        parentColumn = "sizeTypeId",
        entityColumn = "sizeTypeId"
    )
    val sizeVariants: List<SizeVariantEntity>
)
