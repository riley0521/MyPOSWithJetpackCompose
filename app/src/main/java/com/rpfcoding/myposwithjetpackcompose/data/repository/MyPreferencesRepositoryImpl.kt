package com.rpfcoding.myposwithjetpackcompose.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.rpfcoding.myposwithjetpackcompose.domain.repository.MyPreferencesRepository
import com.rpfcoding.myposwithjetpackcompose.util.Constants.PREFERENCES_NAME
import com.rpfcoding.myposwithjetpackcompose.util.Constants.PREF_BUSINESS_ID_KEY
import com.rpfcoding.myposwithjetpackcompose.util.Constants.PREF_TOKEN_KEY
import com.rpfcoding.myposwithjetpackcompose.util.Constants.PREF_USER_ID_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class MyPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : MyPreferencesRepository {

    private object PreferencesKey {
        val userIdKey = intPreferencesKey(name = PREF_USER_ID_KEY)
        val businessIdKey = intPreferencesKey(name = PREF_BUSINESS_ID_KEY)
        val tokenKey = stringPreferencesKey(name = PREF_TOKEN_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveUserId(userId: Int) {
        dataStore.edit { pref ->
            pref[PreferencesKey.userIdKey] = userId
        }
    }

    override fun readUserId(): Flow<Int> {
        return dataStore
            .data
            .catch {
                emit(emptyPreferences())
            }.map { pref ->
                val userId = pref[PreferencesKey.userIdKey] ?: 0
                userId
            }
    }

    override suspend fun saveBusinessId(businessId: Int) {
        dataStore.edit { pref ->
            pref[PreferencesKey.businessIdKey] = businessId
        }
    }

    override fun readBusinessId(): Flow<Int> {
        return dataStore
            .data
            .catch {
                emit(emptyPreferences())
            }.map { pref ->
                val businessId = pref[PreferencesKey.businessIdKey] ?: 0
                businessId
            }
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { pref ->
            pref[PreferencesKey.tokenKey] = token
        }
    }

    override fun readToken(): Flow<String> {
        return dataStore
            .data
            .catch {
                emit(emptyPreferences())
            }.map { pref ->
                val token = pref[PreferencesKey.tokenKey] ?: ""
                token
            }
    }
}