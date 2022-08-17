package com.rpfcoding.myposwithjetpackcompose.di

import android.content.Context
import androidx.room.Room
import com.rpfcoding.myposwithjetpackcompose.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMyDatabase(
        @ApplicationContext context: Context
    ): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "my_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAddressDao(
        db: MyDatabase
    ): AddressDao = db.addressDao()

    @Provides
    @Singleton
    fun provideModuleDao(
        db: MyDatabase
    ): ModuleDao = db.moduleDao()

    @Provides
    @Singleton
    fun providePositionDao(
        db: MyDatabase
    ): PositionDao = db.positionDao()

    @Provides
    @Singleton
    fun provideUserDao(
        db: MyDatabase
    ): UserDao = db.userDao()
}