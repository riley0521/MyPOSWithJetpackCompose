package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BusinessWithCurrenciesEntity(
    @Embedded val business: BusinessEntity,
    @Relation(
        parentColumn = "businessId",
        entityColumn = "businessId"
    )
    val currencies: List<CurrencyEntity>
)
