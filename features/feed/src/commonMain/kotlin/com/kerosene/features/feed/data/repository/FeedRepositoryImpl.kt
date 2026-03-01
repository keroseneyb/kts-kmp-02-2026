package com.kerosene.features.feed.data.repository

import com.kerosene.features.feed.domain.entity.FeedItem
import com.kerosene.features.feed.domain.repository.FeedRepository

class FeedRepositoryImpl : FeedRepository {
    override suspend fun getItems(): List<FeedItem> {
        return List(20) { i ->
            FeedItem(
                id = i,
                title = "Item #$i",
                description = "Description for item $i. This is mock data from MainRepository.",
                imageUrl = "https://picsum.photos/seed/${i + 10}/200/300"
            )
        }
    }
}
