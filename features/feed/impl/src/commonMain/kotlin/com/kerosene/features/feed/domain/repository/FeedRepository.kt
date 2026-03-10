package com.kerosene.features.feed.domain.repository

import com.kerosene.features.feed.domain.entity.Cabinet
import com.kerosene.features.feed.domain.entity.Conversation

interface FeedRepository {
    suspend fun getCabinets(): Result<List<Cabinet>>
    suspend fun getProjectInfo(): Result<Cabinet?>
    suspend fun getConversations(
        isWaitingOnly: Boolean = false,
        limit: Int = 20,
        offset: Int = 0
    ): Result<List<Conversation>>
}
