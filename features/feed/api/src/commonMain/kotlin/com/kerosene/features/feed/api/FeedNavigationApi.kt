package com.kerosene.features.feed.api

import androidx.navigation.NavGraphBuilder

interface FeedNavigationApi {
    fun register(
        navGraphBuilder: NavGraphBuilder,
        onChatClick: (String) -> Unit,
        onBackClick: () -> Unit
    )
}
