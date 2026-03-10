package com.kerosene.features.feed.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kerosene.core.navigation.Feed
import com.kerosene.core.navigation.ConversationDetail
import com.kerosene.features.feed.api.FeedNavigationApi
import com.kerosene.features.feed.presentation.FeedScreen
import com.kerosene.features.feed.presentation.ConversationDetailScreen

class FeedNavigationApiImpl : FeedNavigationApi {
    override fun register(
        navGraphBuilder: NavGraphBuilder,
        onChatClick: (String) -> Unit,
        onBackClick: () -> Unit
    ) {
        navGraphBuilder.composable<Feed> {
            FeedScreen(onChatClick = onChatClick)
        }

        navGraphBuilder.composable<ConversationDetail> { backStackEntry ->
            val conversationDetailRoute = backStackEntry.toRoute<ConversationDetail>()
            ConversationDetailScreen(
                conversationId = conversationDetailRoute.conversationId,
                onBackClick = onBackClick
            )
        }
    }
}
