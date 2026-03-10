package com.kerosene.kts_kmp_02_2026.di

import com.kerosene.core.auth.di.authModule
import com.kerosene.core.database.di.databaseModule
import com.kerosene.core.network.di.networkModule
import com.kerosene.features.login.di.loginModule
import com.kerosene.features.feed.di.feedModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    Napier.base(DebugAntilog())
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            networkModule,
            databaseModule,
            authModule,
            loginModule,
            feedModule
        )
    }
}
