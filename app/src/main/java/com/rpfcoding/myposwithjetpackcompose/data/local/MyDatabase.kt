package com.rpfcoding.myposwithjetpackcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rpfcoding.myposwithjetpackcompose.data.local.dao.*
import com.rpfcoding.myposwithjetpackcompose.data.local.entity.*

@Database(
    entities = [
        AddressEntity::class,
        ModuleEntity::class,
        PositionEntity::class,
        UserEntity::class,
        BusinessEntity::class,
        CurrencyEntity::class,
        UOMEntity::class,
        SizeTypeEntity::class,
        SizeVariantEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun addressDao(): AddressDao
    abstract fun moduleDao(): ModuleDao
    abstract fun positionDao(): PositionDao
    abstract fun userDao(): UserDao
    abstract fun businessDao(): BusinessDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun uomDao(): UOMDao
    abstract fun sizeTypeDao(): SizeTypeDao
    abstract fun sizeVariantDao(): SizeVariantDao
}