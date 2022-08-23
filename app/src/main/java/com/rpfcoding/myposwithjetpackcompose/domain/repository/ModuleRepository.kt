package com.rpfcoding.myposwithjetpackcompose.domain.repository

import com.rpfcoding.myposwithjetpackcompose.domain.model.Module

interface ModuleRepository {

    suspend fun getAll(): List<Module>
}