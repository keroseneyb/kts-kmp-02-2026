package com.kerosene.features.auth.di

import com.kerosene.features.auth.data.repository.LoginRepositoryImpl
import com.kerosene.features.auth.domain.repository.LoginRepository
import com.kerosene.features.auth.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    single<LoginRepository> { LoginRepositoryImpl() }
    viewModelOf(::LoginViewModel)
}
