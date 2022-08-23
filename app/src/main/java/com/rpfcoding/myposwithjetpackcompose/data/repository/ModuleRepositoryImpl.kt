package com.rpfcoding.myposwithjetpackcompose.data.repository

import com.rpfcoding.myposwithjetpackcompose.data.local.dao.ModuleDao
import com.rpfcoding.myposwithjetpackcompose.domain.model.Module
import com.rpfcoding.myposwithjetpackcompose.domain.repository.ModuleRepository
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class ModuleRepositoryImpl @Inject constructor(
    private val moduleDao: ModuleDao,
    private val prefRepository: MyPreferencesRepository
) : ModuleRepository {

    override suspend fun getAll(): List<Module> {
        val userId = prefRepository.readUserId().stateIn(CoroutineScope(coroutineContext)).value

        return moduleDao.getByUserId(userId).map {
            Module(
                name = it.name,
                moduleId = it.moduleId
            )
        }
    }
}