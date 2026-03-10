package com.kerosene.features.login.di

import com.kerosene.features.login.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginViewModel)
}
