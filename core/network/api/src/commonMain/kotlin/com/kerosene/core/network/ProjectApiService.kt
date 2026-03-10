package com.kerosene.core.network

import com.kerosene.core.network.dto.CabinetListResponse
import com.kerosene.core.network.dto.ConversationResponse
import com.kerosene.core.network.dto.ProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class ProjectApiService(
    private val authClient: HttpClient,
    private val sproClient: HttpClient,
    private val cabinetProvider: CabinetProvider
) {

    suspend fun getCabinets(): CabinetListResponse {
        return authClient.get {
            url("api/cabinets/list")
        }.body()
    }

    suspend fun getProjectsList(cabinetId: String): ProjectResponse {
        return sproClient.get {
            url("https://$cabinetId.smartbotpro.ru/api/projects/list")
            header("X-SPro-Cabinet", cabinetId)
        }.body()
    }

    suspend fun getProject(): ProjectResponse {
        val cabinetId = cabinetProvider.getCabinetId() ?: ""
        val projectId = cabinetProvider.getProjectId() ?: ""
        
        return sproClient.get {
            url("https://$cabinetId.smartbotpro.ru/api/project/get")
            header("X-SPro-Cabinet", cabinetId)
            header("X-SPro-Project", projectId)
        }.body()
    }

    suspend fun getConversations(
        cabinetId: String,
        isWaitingOnly: Boolean,
        limit: Int = 20,
        offset: Int = 0
    ): ConversationResponse {
        val currentProject = cabinetProvider.getProjectId() ?: ""
        
        return sproClient.get {
            url("https://$cabinetId.smartbotpro.ru/api/conversations/list")
            header("X-SPro-Cabinet", cabinetId)
            header("X-SPro-Project", currentProject)
            parameter("limit", limit)
            parameter("offset", offset)
            if (isWaitingOnly) {
                parameter("is_read", false)
            }
        }.body()
    }
}
