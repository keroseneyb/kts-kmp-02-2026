package com.kerosene.kts_kmp_02_2026.di

import com.kerosene.core.auth.di.platformAuthModule
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    includes(platformAuthModule)
}
