package com.kerosene.features.feed.di

import com.kerosene.features.feed.api.FeedNavigationApi
import com.kerosene.features.feed.impl.FeedNavigationApiImpl
import com.kerosene.features.feed.data.repository.FeedRepositoryImpl
import com.kerosene.features.feed.domain.repository.FeedRepository
import com.kerosene.features.feed.presentation.FeedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val feedModule = module {
    single<FeedNavigationApi> { FeedNavigationApiImpl() }
    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }
    viewModelOf(::FeedViewModel)
}
