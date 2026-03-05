package com.kerosene.features.feed.domain.repository

import com.kerosene.features.feed.domain.entity.FeedItem

interface FeedRepository {
    suspend fun getItems(): List<FeedItem>
}
