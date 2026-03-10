package com.kerosene.features.feed.data.repository

import com.kerosene.core.network.ProjectApiService
import com.kerosene.core.network.CabinetProvider
import com.kerosene.features.feed.data.mapper.toConversation
import com.kerosene.features.feed.data.mapper.toCabinet
import com.kerosene.features.feed.domain.entity.Conversation
import com.kerosene.features.feed.domain.entity.Cabinet
import com.kerosene.features.feed.domain.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedRepositoryImpl(
    private val projectApiService: ProjectApiService,
    private val spaceProvider: CabinetProvider,
) : FeedRepository {

    override suspend fun getCabinets(): Result<List<Cabinet>> {
        return runCatching {
            val response = projectApiService.getCabinets()
            response.data?.cabinets?.map { it.toCabinet() } ?: emptyList()
        }
    }

    override suspend fun getProjectInfo(): Result<Cabinet?> = runCatching {
        val cabinetId = spaceProvider.getCabinetId() ?: return@runCatching null
        val projectId = spaceProvider.getProjectId() ?: ""

        val projectDto = runCatching { projectApiService.getProject().data?.project }.getOrNull()

        if (projectDto != null) {
            val cabinetName = runCatching { projectApiService.getCabinets().data?.cabinets }
                .getOrNull()?.find { it.id == cabinetId }?.name ?: ""

            projectDto.toCabinet(cabinetId, cabinetName)
        } else {
            createFallbackCabinet(cabinetId, projectId)
        }
    }

    private fun createFallbackCabinet(cabinetId: String, projectId: String): Cabinet? {
        if (cabinetId.isEmpty()) return null
        return Cabinet(
            id = projectId,
            name = "",
            cabinetId = cabinetId,
            cabinetName = "",
            projectId = projectId,
            imageUrl = ""
        )
    }

    override suspend fun getConversations(
        isWaitingOnly: Boolean,
        limit: Int,
        offset: Int,
    ): Result<List<Conversation>> = runCatching {
        withContext(Dispatchers.IO) {
            val cabinet = spaceProvider.getCabinetId() ?: ""
            val response = projectApiService.getConversations(cabinet, isWaitingOnly, limit, offset)
            response.data.conversations.map { it.toConversation() }
        }
    }
}
