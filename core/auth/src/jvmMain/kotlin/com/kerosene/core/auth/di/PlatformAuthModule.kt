package com.kerosene.core.auth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import java.io.File

val platformAuthModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { 
                val userHome = System.getProperty("user.home")
                val dataDir = File(userHome, ".spro_auth")
                if (!dataDir.exists()) dataDir.mkdirs()
                File(dataDir, "auth_prefs.preferences_pb")
            }
        )
    }
}
