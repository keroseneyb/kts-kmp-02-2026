package com.kerosene.features.feed.presentation

import com.kerosene.features.feed.domain.entity.FeedItem

data class FeedUiState(
    val items: List<FeedItem> = emptyList()
)