package com.kerosene.core.auth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kerosene.core.auth.domain.repository.AuthPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPrefsImpl(private val dataStore: DataStore<Preferences>) : AuthPrefs {

    private val SESSION_COOKIE_KEY = stringPreferencesKey("spro_session")
    private val CABINET_ID_KEY = stringPreferencesKey("spro_cabinet")
    private val PROJECT_ID_KEY = stringPreferencesKey("spro_project")

    override fun getSessionCookie(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[SESSION_COOKIE_KEY]
    }

    override suspend fun saveSessionCookie(cookie: String) {
        dataStore.edit { preferences ->
            preferences[SESSION_COOKIE_KEY] = cookie
        }
    }

    override suspend fun clearSessionCookie() {
        dataStore.edit { preferences ->
            preferences.remove(SESSION_COOKIE_KEY)
        }
    }

    override fun getCabinetId(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[CABINET_ID_KEY]
    }

    override fun getProjectId(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[PROJECT_ID_KEY]
    }

    override suspend fun saveCabinet(cabinetId: String, projectId: String) {
        dataStore.edit { preferences ->
            preferences[CABINET_ID_KEY] = cabinetId
            preferences[PROJECT_ID_KEY] = projectId
        }
    }

    override suspend fun clearCabinet() {
        dataStore.edit { preferences ->
            preferences.remove(CABINET_ID_KEY)
            preferences.remove(PROJECT_ID_KEY)
        }
    }
}