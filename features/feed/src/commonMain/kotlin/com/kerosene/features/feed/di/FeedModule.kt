package com.kerosene.features.feed.di

import com.kerosene.features.feed.data.repository.FeedRepositoryImpl
import com.kerosene.features.feed.domain.repository.FeedRepository
import com.kerosene.features.feed.presentation.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    single<FeedRepository> { FeedRepositoryImpl() }
    viewModelOf(::MainViewModel)
}
