package com.kerosene.kts_kmp_02_2026

import android.app.Application
import com.kerosene.kts_kmp_02_2026.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
        }
    }
}
