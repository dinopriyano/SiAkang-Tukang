package com.siakang.tukang.core.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
            preferences.remove(SKILLS)
        }
    }

    val Uid: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[UID] ?: ""
        }

    val skills: Flow<List<String>>
        get() = context.dataStore.data.map { preferences ->
            (preferences[SKILLS] ?: "").split(",")
        }



    companion object {
        val UID = stringPreferencesKey("UID")
        val SKILLS = stringPreferencesKey("SKILLS")
        private const val DATASTORE_FILE = "siakang-tukang.pb"
    }

}