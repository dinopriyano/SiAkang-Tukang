package com.siakang.tukang.core.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_FILE)

    suspend fun <T> storeData(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.remove(UID)
            preferences.remove(ARE_DATA_COMPLETE)
        }
    }

    val Uid: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[UID] ?: ""
        }

    val areDataComplete: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[ARE_DATA_COMPLETE] ?: false
        }



    companion object {
        val UID = stringPreferencesKey("UID")
        val ARE_DATA_COMPLETE = booleanPreferencesKey("ARE_DATA_COMPLETE")
        private const val DATASTORE_FILE = "siakang-tukang.pb"
    }

}