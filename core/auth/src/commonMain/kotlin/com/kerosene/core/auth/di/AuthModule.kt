package com.kerosene.core.auth.di

import com.kerosene.core.auth.api.TokenProvider
import com.kerosene.core.auth.data.local.AuthTokenProvider
import com.kerosene.core.auth.domain.repository.AuthPrefs
import com.kerosene.core.auth.data.local.AuthCabinetProvider
import com.kerosene.core.auth.data.repository.AuthPrefsImpl
import com.kerosene.core.auth.data.repository.AuthRepositoryImpl
import com.kerosene.core.auth.domain.repository.AuthRepository
import com.kerosene.core.network.CabinetProvider
import org.koin.dsl.module

val authModule = module {
    single<AuthPrefs> { AuthPrefsImpl(get()) }
    single<TokenProvider> { AuthTokenProvider(get()) }
    single<CabinetProvider> { AuthCabinetProvider(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
