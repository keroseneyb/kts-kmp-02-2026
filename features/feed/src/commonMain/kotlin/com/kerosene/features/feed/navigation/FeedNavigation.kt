package com.kerosene.features.feed.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kerosene.core.common.navigation.Feed
import com.kerosene.features.feed.presentation.FeedScreen

fun NavGraphBuilder.feedScreen() {
    composable<Feed> {
        FeedScreen()
    }
}
