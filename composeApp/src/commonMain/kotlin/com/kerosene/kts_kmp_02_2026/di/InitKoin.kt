package com.kerosene.kts_kmp_02_2026.di

import com.kerosene.core.database.di.databaseModule
import com.kerosene.core.network.di.networkModule
import com.kerosene.features.auth.di.loginModule
import com.kerosene.features.feed.di.mainModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            networkModule,
            databaseModule,
            loginModule,
            mainModule
        )
    }
