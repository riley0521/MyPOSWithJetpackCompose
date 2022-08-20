package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Suppress("unused")
@Entity
data class BusinessWithCurrenciesUnitsSizesEntity(
    @Embedded val business: BusinessEntity,
    @Relation(
        parentColumn = "businessId",
        entityColumn = "businessId"
    )
    val currencies: List<CurrencyEntity>,
    @Relation(
        parentColumn = "businessId",
        entityColumn = "businessId"
    )
    val units: List<UOMEntity>,
    @Relation(
        parentColumn = "businessId",
        entityColumn = "businessId"
    )
    val sizeTypes: List<SizeTypeEntity>
)
